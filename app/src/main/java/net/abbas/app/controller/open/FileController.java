package net.abbas.app.controller.open;

import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dto.file.FileDto;
import net.abbas.service.file.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService service;

    @Value("${app.file.upload.path}")
    private String uploadPath;

    @Autowired
    public FileController(FileService service) {
        this.service = service;
    }

    @GetMapping("{name}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String name) throws FileNotFoundException {
        try {
            FileDto dto  = service.readByName(name);
            File file = new File(uploadPath+File.separator+dto.getPath());
            if (!file.exists()) {
                throw new ValidationExceptions("file not found");
            }
            InputStream stream = new FileInputStream(file);
            InputStreamResource resource = new InputStreamResource(stream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(dto.getContentType()));
            if (dto.getSize()!=null) {
                headers.setContentLength(dto.getSize());
            }
            return new ResponseEntity<>(resource,headers, HttpStatus.OK);
        }catch (FileNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        }
}
