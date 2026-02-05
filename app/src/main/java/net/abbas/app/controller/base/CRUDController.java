package net.abbas.app.controller.base;

import net.abbas.app.model.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CRUDController<Dto> extends CreateController<Dto>
, UpdateController<Dto>
, DeleteController<Dto>
,ReadController<Dto>{
}
