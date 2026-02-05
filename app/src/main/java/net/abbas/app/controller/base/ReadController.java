package net.abbas.app.controller.base;

import net.abbas.app.model.APIPaginationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReadController<Dto>{
    @GetMapping("")
    APIPaginationResponse<List<Dto>> getAll(@RequestParam(required = false) Integer page
            , @RequestParam(required = false) Integer size);
}
