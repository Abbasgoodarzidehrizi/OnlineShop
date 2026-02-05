package net.abbas.app.controller.panel.product;

import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.CRUDController;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.product.SizeDto;
import net.abbas.service.product.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/service")
public class SizePanelController implements CRUDController<SizeDto> {
    private final SizeService service;

    @Autowired
    public SizePanelController(SizeService service) {
        this.service = service;
    }

    @CheckPermission("service_add")

    @Override
    public APIResponse<SizeDto> create(SizeDto dto) {
        try {
            return APIResponse.<SizeDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(service.create(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<SizeDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("service_delete")
    @Override
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .message("Ok")
                .data(service.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @CheckPermission("service_list")
    @Override
    public APIPaginationResponse<List<SizeDto>> getAll(Integer page, Integer size) {
        Page<SizeDto> SizeDto = service.readAll(page, size);
        return APIPaginationResponse.<List<SizeDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(SizeDto.getContent())
                .totalCount(SizeDto.getTotalElements())
                .totalPage(SizeDto.getTotalPages())
                .build();
    }

    @CheckPermission("service_edit")
    @Override
    public APIResponse<SizeDto> update(SizeDto dto) {
        try {
            return APIResponse.<SizeDto>builder()
                    .message("Ok")
                    .data(service.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<SizeDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }

}
