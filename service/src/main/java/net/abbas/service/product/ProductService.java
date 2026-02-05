package net.abbas.service.product;

import net.abbas.common.exceptions.NotFoundException;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.file.File;
import net.abbas.dataaccess.entity.product.Product;
import net.abbas.dataaccess.repository.product.ProductCategoryRepository;
import net.abbas.dataaccess.repository.product.ProductRepository;
import net.abbas.dto.product.LimittedProductDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.enums.ProductQueryType;
import net.abbas.service.base.CRUDService;
import net.abbas.service.base.HasValidation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements CRUDService<ProductDto>, HasValidation<ProductDto> {
    private final ProductRepository productRepository;
    private final ProductCategoryRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository repository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<ProductDto> readAllProducts(ProductQueryType type) {
        List<Product> result = new ArrayList<>();
        switch (type) {
            case Popular -> {
                result = productRepository.find6PopularProducts();
            }
            case Newest -> {
                result = productRepository.find6NewestProducts();
            }
            case Cheapest -> {
                result = productRepository.find6CheapestProducts();
            }
            case Expensive -> {
                result = productRepository.find6ExpensiveProducts();
            }

        }
        return result.stream().map(x -> modelMapper.map(x, ProductDto.class)).toList();
    }

    public ProductDto readProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public Page<ProductDto> readAll(Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return repository.findAll(Pageable.ofSize(size).withPage(page))
                .map(x->modelMapper.map(x, ProductDto.class));
    }


    @Override
    public ProductDto create(ProductDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("product must not be null");
        }
        checkValidations(dto);
        Product savedProduct = modelMapper.map(dto, Product.class);
        savedProduct.setEnabled(true);
        savedProduct.setVisitCount(0L);
        savedProduct.setExisted(true);
        savedProduct.setAddDate(LocalDateTime.now());
        return modelMapper.map(savedProduct,ProductDto.class);
    }

    @Override
    public Boolean delete(Long id) {
        repository.deleteById(id);
        return true;
    }



    @Override
    public ProductDto update(ProductDto dto) {
        if (dto.getId() == null || dto.getId() <= 0) {
            throw new NotFoundException();
        }
        Product oldData = productRepository.findById(dto.getId()).orElseThrow(NotFoundException::new);
        oldData.setTitle(Optional.ofNullable(dto.getTitle()).orElse(oldData.getTitle()));
        oldData.setPrice(Optional.ofNullable(dto.getPrice()).orElse(oldData.getPrice()));
        oldData.setDescription(Optional.ofNullable(dto.getDescription()).orElse(oldData.getDescription()));
        oldData.setExisted(Optional.ofNullable(dto.getExists()).orElse(oldData.getExisted()));
        oldData.setEnabled(Optional.ofNullable(dto.getEnabled()).orElse(oldData.getEnabled()));

        if (dto.getImage()!=null) {
            oldData.setImage(Optional.ofNullable(modelMapper.map(dto.getImage(), File.class)).orElse(oldData.getImage()));
        }
        return modelMapper.map(productRepository.save(oldData), ProductDto.class);
    }


    public Page<ProductDto> readAllCategories(Long categoryId, Integer page, Integer size) {
        if (page == null) {
            page = 0;
        }
        if (size == null) {
            size = 10;
        }
        return productRepository.findAllByProductCategories_Id(categoryId, Pageable.ofSize(size)
                .withPage(page)).map(x->modelMapper.map(x, ProductDto.class));
    }

    public Page<LimittedProductDto> readAllCategory(Long categoryId,Integer page,Integer size) {
        return readAllCategories(categoryId,page,size).map(x->modelMapper.map(x,LimittedProductDto.class));
    }

    @Override
    public void checkValidations(ProductDto dto) {
        if (dto == null) {
            throw new ValidationExceptions("Please fill data");
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new ValidationExceptions("Please enter Title");
        }
        if (dto.getPrice() == null || dto.getPrice() < 0) {
            throw new ValidationExceptions("Please enter price");
        }
    }
}
