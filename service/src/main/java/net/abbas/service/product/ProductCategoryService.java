package net.abbas.service.product;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.file.File;
import net.abbas.dataaccess.entity.product.ProductCategory;
import net.abbas.dataaccess.repository.product.ProductCategoryRepository;
import net.abbas.dto.product.ProductCategoryDto;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService implements CRUDService<ProductCategoryDto>, HasValidation<ProductCategoryDto> {
    private final ProductCategoryRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductCategoryService(ProductCategoryRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public ProductCategoryDto readProductCategoryById(Long id) {
        ProductCategory pcate = repository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(pcate, ProductCategoryDto.class);
    }

    public List<ProductCategoryDto> readAllActive(){
      return repository.findAllByEnabledIsTrue().stream()
              .map(x->modelMapper.map(x,ProductCategoryDto.class)).toList();
    }


    @Override
    public ProductCategoryDto create(ProductCategoryDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("ProductCategory must not be null");
        }
        checkValidations(dto);
        ProductCategory save = modelMapper.map(dto, ProductCategory.class);
        return modelMapper.map(repository.save(save), ProductCategoryDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public Page<ProductCategoryDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }

       return repository.findAll(Pageable.ofSize(size).withPage(page)).map(x->modelMapper.map(x, ProductCategoryDto.class));
    }

    @Override
    public ProductCategoryDto update(ProductCategoryDto dto) {
        if (dto.getId() == null || dto.getId() <= 0) {
            throw new NotFoundException();
        }
        checkValidations(dto);
       ProductCategory oldData = repository.findById(dto.getId()).orElseThrow(NotFoundException::new);
       oldData.setTitle(Optional.ofNullable(dto.getTitle()).orElse(oldData.getTitle()));
       oldData.setDescription(Optional.ofNullable(dto.getDescription()).orElse(oldData.getDescription()));
        if (dto.getImage() != null) {
            oldData.setImages(Optional.ofNullable(modelMapper.map(dto.getImage(), File.class)).orElse(oldData.getImages()));
        }

       repository.save(oldData);
        return modelMapper.map(oldData , ProductCategoryDto.class);
    }


    @Override
    public void checkValidations(ProductCategoryDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("Please fill data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new ValidationExceptions("Please enter Title");
        }
        if (dto.getDescription() == null) {
            throw new ValidationExceptions("Please enter price");
        }
        if (dto.getImage() == null) {
            throw new ValidationExceptions("Please enter price");
        }
    }
}
