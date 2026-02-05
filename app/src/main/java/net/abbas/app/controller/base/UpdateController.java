package net.abbas.app.controller.base;

import net.abbas.app.model.APIResponse;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdateController<Dto>{
    @PutMapping("edit")
    APIResponse<Dto> update(@RequestBody Dto dto);
}
