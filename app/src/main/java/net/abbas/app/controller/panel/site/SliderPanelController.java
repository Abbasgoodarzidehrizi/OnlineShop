package net.abbas.app.controller.panel.site;

import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.controller.base.CRUDController;
import net.abbas.app.model.APIPaginationResponse;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.site.SliderDto;
import net.abbas.service.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/slider")
public class SliderPanelController implements CRUDController<SliderDto> {
    private final SliderService slider;

    @Autowired
    public SliderPanelController(SliderService slider) {
        this.slider = slider;
    }

    @CheckPermission("slider_add")
    @Override
    //todo
    public APIResponse<SliderDto> create(SliderDto dto) {
        try {
            return APIResponse.<SliderDto>builder()
                    .status(APIStatus.Success)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return APIResponse.<SliderDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

    @CheckPermission("slider_delete")
    @Override
    public APIResponse<Boolean> delete(long id) {
        return APIResponse.<Boolean>builder()
                .message("Ok")
                .data(slider.delete(id))
                .status(APIStatus.Success)
                .build();
    }

    @CheckPermission("slider_list")
    @Override
    public APIPaginationResponse<List<SliderDto>> getAll(Integer page, Integer size) {
        Page<SliderDto> SliderDto = slider.readAll(page, size);
        return APIPaginationResponse.<List<SliderDto>>builder()
                .status(APIStatus.Success)
                .message("OK")
                .data(SliderDto.getContent())
                .totalCount(SliderDto.getTotalElements())
                .totalPage(SliderDto.getTotalPages())
                .build();
    }

    @CheckPermission("slider_edit")
    @Override
    public APIResponse<SliderDto> update(SliderDto dto) {
        try {
            return APIResponse.<SliderDto>builder()
                    .message("Ok")
                    .data(slider.update(dto))
                    .status(APIStatus.Success)
                    .build();
        } catch (Exception e) {
            return APIResponse.<SliderDto>builder()
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();

        }
    }
}
