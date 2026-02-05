package net.abbas.service.base;

import net.abbas.dto.order.InvoiceDto;
import net.abbas.dto.product.ProductDto;

public interface HasValidation <Dto>{
    void checkValidations(Dto dto);
}
