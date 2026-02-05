package net.abbas.app.controller.open;

import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.common.exceptions.NotFoundException;
import net.abbas.dto.product.LimittedProductDto;
import net.abbas.dto.product.ProductCategoryDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.enums.ProductQueryType;
import net.abbas.service.product.ProductCategoryService;
import net.abbas.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;
    private final ProductCategoryService categoryService;

    @Autowired
    public ProductController(ProductService service, ProductCategoryService categoryService) {
        this.service = service;
        this.categoryService = categoryService;
    }

    @GetMapping("category")
    public APIResponse<List<ProductCategoryDto>> getCategory(@RequestParam Long category_id, @PathVariable Integer page, @PathVariable Integer size) {
        return APIResponse.<List<ProductCategoryDto>>builder()
                .data(categoryService.readAllActive())
                .message("Ok")
                .status(APIStatus.Success)
                .build();
    }

    @GetMapping("top/{type}")
    public APIResponse<List<ProductDto>> getTopCategory(@PathVariable ProductQueryType type) {
        return APIResponse.<List<ProductDto>>builder()
                .data(service.readAllProducts(type))
                .message("Ok")
                .status(APIStatus.Success)
                .build();
    }

    @GetMapping("cat/{cid}")
    public APIPaginationResponse<List<LimittedProductDto>> getTopCategory(@PathVariable Long cid
            ,@RequestParam(required = false) Integer page
            ,@RequestParam(required = false)  Integer size) {

        Page<LimittedProductDto> productDtoPage = service.readAllCategory(cid, page, size);
        return APIPaginationResponse.<List<LimittedProductDto>>builder()
                .data(productDtoPage.getContent())
                .message("Ok")
                .status(APIStatus.Success)
                .totalCount(productDtoPage.getTotalElements())
                .totalPage(productDtoPage.getTotalPages())
                .build();
    }

    @GetMapping("{id}")
    public APIResponse<ProductDto> getProductById(@PathVariable Long id){
        try{
            return APIResponse.<ProductDto>builder()
                    .data(service.readProductById(id))
                    .message("Ok")
                    .status(APIStatus.Success)
                    .build();
        } catch (NotFoundException e){
            return APIResponse.<ProductDto>builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }


    }

}
