package net.abbas.service.product;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.product.Size;
import net.abbas.dataaccess.repository.product.SizeRepository;
import net.abbas.dto.product.SizeDto;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SizeService implements CRUDService<SizeDto>, HasValidation<SizeDto> {
    private final SizeRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public SizeService(SizeRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public SizeDto readSizeById(Long id) {
        Size size = repository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(size, SizeDto.class);
    }


    @Override
    public SizeDto create(SizeDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("Size must not be null");
        }
        checkValidations(dto);
        Size save = modelMapper.map(dto, Size.class);
        return modelMapper.map(repository.save(save), SizeDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public Page<SizeDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }

       return repository.findAll(Pageable.ofSize(size).withPage(page)).map(x->modelMapper.map(x, SizeDto.class));
    }

    @Override
    public SizeDto update(SizeDto dto) {
        if (dto.getId() == null || dto.getId() <= 0) {
            throw new NotFoundException();
        }
        checkValidations(dto);
       Size oldData = repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
       oldData.setTitle(Optional.ofNullable(dto.getTitle()).orElse(oldData.getTitle()));
       oldData.setDescription(Optional.ofNullable(dto.getDescription()).orElse(oldData.getDescription()));
       repository.save(oldData);
        return modelMapper.map(oldData , SizeDto.class);
    }


    @Override
    public void checkValidations(SizeDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("Please fill data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new ValidationExceptions("Please enter Title");
        }
        if (dto.getDescription() == null) {
            throw new ValidationExceptions("Please enter price");
        }
    }
}
