package net.abbas.app.controller.panel.product;

import jakarta.servlet.http.HttpServletRequest;
import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.CRUDController;
import net.abbas.app.filter.JwtFilter;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dataaccess.entity.product.Product;
import net.abbas.dto.order.InvoiceDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.dto.user.UserDto;
import net.abbas.service.order.InvoiceService;
import net.abbas.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/panel/product")
public class ProductPanelController implements CRUDController<ProductDto> {
    private final ProductService product;

    @Autowired
    public ProductPanelController(ProductService product) {
        this.product = product;
    }

    @CheckPermission("product_add")

    @Override
    public APIResponse<ProductDto> create(ProductDto dto) {
        try {
            return APIResponse.<ProductDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(product.create(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<ProductDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("product_delete")
    @Override
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .message("Ok")
                .data(product.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @CheckPermission("product_list")
    @Override
    public APIPaginationResponse<List<ProductDto>> getAll(Integer page, Integer size) {
        Page<ProductDto> ProductDto = product.readAll(page, size);
        return APIPaginationResponse.<List<ProductDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(ProductDto.getContent())
                .totalCount(ProductDto.getTotalElements())
                .totalPage(ProductDto.getTotalPages())
                .build();
    }

    @CheckPermission("product_edit")
    @Override
    public APIResponse<ProductDto> update(ProductDto dto) {
        try {
            return APIResponse.<ProductDto>builder()
                    .message("Ok")
                    .data(product.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ProductDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }

    @GetMapping("cat/{cid}")
    @CheckPermission("list_product")
    public APIPaginationResponse<List<ProductDto>> cateListProduct(@PathVariable Long cid
            , Integer page
            , Integer size) {
            Page<ProductDto> ProductDto = product.readAllCategories(cid, page, size);
            return APIPaginationResponse.<List<ProductDto>>builder()
                    .totalPage(ProductDto.getTotalPages())
                    .totalCount(ProductDto.getTotalElements())
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(ProductDto.getContent())
                    .build();
    }
}
