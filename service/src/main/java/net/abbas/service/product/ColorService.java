package net.abbas.service.product;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.product.Color;
import net.abbas.dataaccess.repository.product.ColorRepository;
import net.abbas.dto.product.ColorDto;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ColorService implements CRUDService<ColorDto>, HasValidation<ColorDto> {
    private final ColorRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ColorService(ColorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public ColorDto readColorById(Long id) {
        Color product = repository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(product, ColorDto.class);
    }


    @Override
    public ColorDto create(ColorDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("Color must not be null");
        }
        checkValidations(dto);
        Color save = modelMapper.map(dto, Color.class);
        return modelMapper.map(repository.save(save),ColorDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public Page<ColorDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }

       return repository.findAll(Pageable.ofSize(size).withPage(page)).map(x->modelMapper.map(x, ColorDto.class));
    }

    @Override
    public ColorDto update(ColorDto dto) {
        if (dto.getId() == null || dto.getId() <= 0) {
            throw new NotFoundException();
        }
        checkValidations(dto);
       Color oldData = repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
       oldData.setHex(Optional.ofNullable(dto.getHex()).orElse(oldData.getHex()));
       oldData.setName(Optional.ofNullable(dto.getName()).orElse(oldData.getName()));
       repository.save(oldData);
        return modelMapper.map(oldData , ColorDto.class);
    }


    @Override
    public void checkValidations(ColorDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("Please fill data");
        }
        if (dto.getHex() == null || dto.getHex().isEmpty()) {
            throw new ValidationExceptions("Please enter Title");
        }
        if (dto.getName() == null) {
            throw new ValidationExceptions("Please enter price");
        }
    }
}
