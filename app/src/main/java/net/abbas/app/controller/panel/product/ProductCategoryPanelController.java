package net.abbas.app.controller.panel.product;

import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.*;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.product.ProductCategoryDto;
import net.abbas.dto.product.ProductCategoryDto;
import net.abbas.service.product.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/product/category")
public class ProductCategoryPanelController implements CreateController<ProductCategoryDto>
, UpdateController<ProductCategoryDto>
, ReadController<ProductCategoryDto> {
    private final ProductCategoryService productCategory;

    @Autowired
    public ProductCategoryPanelController(ProductCategoryService productCategory) {
        this.productCategory = productCategory;
    }

    @CheckPermission("product_category_add")
    @Override
    public APIResponse<ProductCategoryDto> create(ProductCategoryDto dto) {
        try {
            return APIResponse.<ProductCategoryDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(productCategory.create(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<ProductCategoryDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("product_category_list")
    @Override
    public APIPaginationResponse<List<ProductCategoryDto>> getAll(Integer page, Integer size) {
        Page<ProductCategoryDto> ProductCategoryDto = productCategory.readAll(page, size);
        return APIPaginationResponse.<List<ProductCategoryDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(ProductCategoryDto.getContent())
                .totalCount(ProductCategoryDto.getTotalElements())
                .totalPage(ProductCategoryDto.getTotalPages())
                .build();
    }

    @CheckPermission("product_category_edit")
    @Override
    public APIResponse<ProductCategoryDto> update(ProductCategoryDto dto) {
        try {
            return APIResponse.<ProductCategoryDto>builder()
                    .message("Ok")
                    .data(productCategory.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ProductCategoryDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }
}
