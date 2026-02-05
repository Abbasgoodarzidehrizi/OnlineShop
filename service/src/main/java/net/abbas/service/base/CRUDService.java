package net.abbas.service.base;

import net.abbas.dto.product.ProductDto;
import net.abbas.dto.site.NavDto;

public interface CRUDService<Dto> extends
CreateService<Dto>,
DeleteService<Dto>,
UpdateService<Dto>,
ReadService<Dto>{
}
