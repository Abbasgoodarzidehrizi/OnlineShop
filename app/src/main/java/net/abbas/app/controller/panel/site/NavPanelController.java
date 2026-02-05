package net.abbas.app.controller.panel.site;

import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.CRUDController;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.site.NavDto;
import net.abbas.service.site.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/nav")
public class NavPanelController implements CRUDController<NavDto> {
    private final NavService nav;

    @Autowired
    public NavPanelController(NavService nav) {
        this.nav = nav;
    }

    @CheckPermission("nav_add")
    @Override
    public APIResponse<NavDto> create(NavDto dto) {
        try {
            return APIResponse.<NavDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .data(nav.create(dto))
                    .build();
        } catch (Exception e) {
            return APIResponse.<NavDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("nav_delete")
    @Override
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .message("Ok")
                .data(nav.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @CheckPermission("nav_list")
    @Override
    public APIPaginationResponse<List<NavDto>> getAll(Integer page, Integer size) {
        Page<NavDto> NavDto = nav.readAll(page, size);
        return APIPaginationResponse.<List<NavDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(NavDto.getContent())
                .totalCount(NavDto.getTotalElements())
                .totalPage(NavDto.getTotalPages())
                .build();
    }

    @CheckPermission("nav_edit")
    @Override
    public APIResponse<NavDto> update(NavDto dto) {
        try {
            return APIResponse.<NavDto>builder()
                    .message("Ok")
                    .data(nav.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<NavDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }
}
