package net.abbas.service.site;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.site.Content;
import net.abbas.dataaccess.repository.site.ContentRepository;
import net.abbas.dto.site.ContentDto;
import net.abbas.service.base.CreateService;
import net.abbas.service.base.HasValidation;
import net.abbas.service.base.ReadService;
import net.abbas.service.base.UpdateService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContentService implements CreateService<ContentDto> ,
        UpdateService<ContentDto> ,
        ReadService<ContentDto>, HasValidation<ContentDto> {
    private final ContentRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContentService(ContentRepository repository, ModelMapper modelMapper) {
        this.repository = repository;

        this.modelMapper = modelMapper;
    }

    public List<ContentDto> readAll() {
        return repository.findAll()
                .stream().map(x -> modelMapper.map(x, ContentDto.class)).toList();
    }

    public ContentDto readByKeyName(String keyName) {
        Content contentDto = repository.findFirstByKeyNameEqualsIgnoreCase(keyName).orElseThrow(NotFoundException::new);
        return modelMapper.map(contentDto, ContentDto.class);
    }


    @Override
    public ContentDto create(ContentDto contentDto) {
        checkValidations(contentDto);
        Content blog = modelMapper.map(contentDto, Content.class);
        repository.save(blog);
        return modelMapper.map(blog, ContentDto.class);
    }

    @Override
    public Page<ContentDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->modelMapper.map(x,ContentDto.class));
    }

    @Override
    public ContentDto update(ContentDto dto) {
        checkValidations(dto);
        if (dto.getId()==null || dto.getId()<=0) {
            throw new ValidationExceptions("blog is required");
        }
        Content contentDto = repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        contentDto.setKeyName(Optional.ofNullable(dto.getKeyName()).orElse(contentDto.getKeyName()));
        contentDto.setValueContent(Optional.ofNullable(dto.getValueContent()).orElse(contentDto.getValueContent()));
        repository.save(contentDto);
        return modelMapper.map(contentDto,ContentDto.class);
    }


    @Override
    public void checkValidations(ContentDto dto) {
        if (dto.getValueContent()==null || dto.getKeyName().isEmpty()) {
            throw new ValidationExceptions("value content is required");
        }

        if (dto.getKeyName()==null || dto.getKeyName().isEmpty()) {
            throw new ValidationExceptions("value keyname is required");
        }
    }
}
