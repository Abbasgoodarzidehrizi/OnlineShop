package net.abbas.app.controller.panel.product;

import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.CRUDController;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.product.ColorDto;
import net.abbas.service.product.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/color")
public class ColorPanelController implements CRUDController<ColorDto> {
    private final ColorService color;

    @Autowired
    public ColorPanelController(ColorService color) {
        this.color = color;
    }

    @CheckPermission("color_add")

    @Override
    public APIResponse<ColorDto> create(ColorDto dto) {
        try {
            return APIResponse.<ColorDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(color.create(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<ColorDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("color_delete")
    @Override
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .message("Ok")
                .data(color.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @CheckPermission("color_list")
    @Override
    public APIPaginationResponse<List<ColorDto>> getAll(Integer page, Integer size) {
        Page<ColorDto> ColorDto = color.readAll(page, size);
        return APIPaginationResponse.<List<ColorDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(ColorDto.getContent())
                .totalCount(ColorDto.getTotalElements())
                .totalPage(ColorDto.getTotalPages())
                .build();
    }

    @CheckPermission("color_edit")
    @Override
    public APIResponse<ColorDto> update(ColorDto dto) {
        try {
            return APIResponse.<ColorDto>builder()
                    .message("Ok")
                    .data(color.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<ColorDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }
}
