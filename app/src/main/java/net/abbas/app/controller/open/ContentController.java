package net.abbas.app.controller.open;

import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.common.exceptions.NotFoundException;
import net.abbas.dto.site.ContentDto;
import net.abbas.service.site.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {
    private final ContentService service;

    @Autowired
    public ContentController(ContentService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<ContentDto>> getAll() {
        return APIResponse.<List<ContentDto>>builder()
                .message("ok")
                .data(service.readAll())
                .build();
    }

    @GetMapping("{key}")
    public APIResponse<ContentDto> getByKey(@PathVariable("key") String key) {
        try {
            return APIResponse.<ContentDto>builder()
                    .data(service.readByKeyName(key))
                    .message("ok")
                    .status(APIStatus.Success)
                    .build();
        }catch (NotFoundException e){
            return APIResponse.<ContentDto>builder()
                    .data(null)
                    .message(e.getMessage())
                    .status(APIStatus.Error)
                    .build();
        }
    }
}
