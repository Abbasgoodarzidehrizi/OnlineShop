package net.abbas.app.controller.base;

import net.abbas.app.model.APIResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DeleteController<Dto>{
    @DeleteMapping("{id}")
    APIResponse<Boolean> delete(@PathVariable long id);
}
