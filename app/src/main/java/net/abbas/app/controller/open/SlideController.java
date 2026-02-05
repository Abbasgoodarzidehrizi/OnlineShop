package net.abbas.app.controller.open;

import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.site.SliderDto;
import net.abbas.service.site.SliderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/slider")
public class SlideController {

    private final SliderService service;

    @Autowired
    public SlideController(SliderService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<SliderDto>> getAll() {
        return APIResponse.<List<SliderDto>>builder()
                .data(service.readAll())
                .status(APIStatus.Success)
                .message("Ok")
                .build();
    }
}
