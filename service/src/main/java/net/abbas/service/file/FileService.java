package net.abbas.service.file;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.repository.file.FileRepository;
import net.abbas.dto.file.FileDto;
import net.abbas.service.base.DeleteService;
import net.abbas.service.base.ReadService;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import net.abbas.dataaccess.entity.file.File;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class FileService implements ReadService<FileDto>, DeleteService<FileDto> {
    private final FileRepository fileRepository;

    @Value("${app.file.upload.path}")
    private String pathname;
    private final ModelMapper mapper;
    @Autowired
    public FileService(FileRepository fileRepository, ModelMapper mapper) {
        this.fileRepository = fileRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<FileDto> readAll(Integer page , Integer size) {
        if (page == null){
            page = 0;
        }
        if (size == null){
            size = 10;
        }
        return fileRepository.findAll(Pageable.ofSize(size).withPage(page)).map(x->mapper.map(x,FileDto.class));
    }


    public FileDto uploadFile(MultipartFile file) throws Exception {

        if (file == null) {
            throw new ValidationExceptions("Please Select File To Upload");
        }

        String head = Objects.requireNonNull(file.getOriginalFilename()).substring(0, file.getOriginalFilename().lastIndexOf("."));
        String extension = Objects.requireNonNull(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1));
        String filename = head +"."+ extension;

        File fileEntity = File.builder()
                .created(LocalDateTime.now())
                .extension(extension)
                .name(head)
                .path(filename)
                .uuid(UUID.randomUUID().toString())
                .size(file.getSize())
                .contentType(file.getContentType())
                .build();

        String path = pathname+ java.io.File.separator + filename;
        Path pathname = Paths.get(path);
        java.nio.file.Files.write(pathname, file.getBytes());

        File saved = fileRepository.save(fileEntity);
        return mapper.map(saved, FileDto.class);
    }

    public FileDto readByName (String name){
        File file = fileRepository.findByNameEqualsIgnoreCase(name).orElseThrow(NotFoundException::new);
        return mapper.map(file, FileDto.class);
    }

    public Boolean delete(Long id){
        fileRepository.deleteById(id);
        return true;
    }

}

