package net.abbas.app.controller.base;

import net.abbas.app.model.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreateController <Dto>{
    @PostMapping("add")
    APIResponse<Dto> create(@RequestBody Dto dto);
}
