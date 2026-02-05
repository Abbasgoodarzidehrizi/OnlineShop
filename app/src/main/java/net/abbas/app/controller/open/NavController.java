package net.abbas.app.controller.open;

import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.dto.site.NavDto;
import net.abbas.service.site.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nav")
public class NavController {
    private final NavService service;

    @Autowired
    public NavController(NavService service) {
        this.service = service;
    }

    @GetMapping("")
    public APIResponse<List<NavDto>> getAll(){
        return APIResponse.<List<NavDto>>builder()
                .data(service.readAll())
                .message("Ok")
                .status(APIStatus.Success)
                .build();
    }
}
