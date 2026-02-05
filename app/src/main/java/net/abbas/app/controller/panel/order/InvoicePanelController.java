package net.abbas.app.controller.panel.order;

import jakarta.servlet.http.HttpServletRequest;
import net.abbas.app.annotation.CheckPermission;
import net.abbas.app.filter.JwtFilter;
import net.abbas.app.model.APIResponse;
import net.abbas.app.model.enums.APIStatus;
import net.abbas.common.exceptions.ValidationExceptions;
import net.abbas.dto.order.InvoiceDto;
import net.abbas.dto.user.UserDto;
import net.abbas.service.order.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/panel/invoice")
public class InvoicePanelController {

    private final InvoiceService service;

    @Autowired
    public InvoicePanelController(InvoiceService service) {
        this.service = service;
    }

    @GetMapping("user/{uid}")
    @CheckPermission("list_invoice")
    public APIResponse<List<InvoiceDto>> getAll(@PathVariable("uid") Long uid) {
        return APIResponse.<List<InvoiceDto>>builder()
                .status(APIStatus.Success)
                .message("success")
                .data(service.readByUser(uid))
                .build();
    }

    @GetMapping("{id}")
    @CheckPermission("list_invoice")
    public APIResponse<InvoiceDto> get(@PathVariable("id") Long id) {
        return APIResponse.<InvoiceDto>builder()
                .status(APIStatus.Success)
                .message("success")
                .data(service.read(id))
                .build();
    }

    @GetMapping("mine")
    @CheckPermission("list_my_invoice")
    public APIResponse<List<InvoiceDto>> getMyInvoice(HttpServletRequest request) {
        UserDto user =(UserDto) request.getAttribute(JwtFilter.CURRENT_USER);
        return APIResponse.<List<InvoiceDto>>builder()
                .data(service.readByUser(user.getId()))
                .message("فاکتورهای شما")
                .status(APIStatus.Success)
                .build();
    }

    @GetMapping("mine/{id}")
    @CheckPermission("info_invoice")
    public APIResponse<InvoiceDto> getMyInvoice(@PathVariable("id") Long id, HttpServletRequest request) {
        UserDto dto = (UserDto) request.getAttribute(JwtFilter.CURRENT_USER);

        try {
            InvoiceDto invoice = service.read(id);
            if (!invoice.getUser().getId().equals(dto.getId())) {
                throw new ValidationExceptions("Access Denied");
            }
            return APIResponse.<InvoiceDto>builder()
                    .status(APIStatus.Success)
                    .message("success")
                    .data(invoice)
                    .build();
        }catch (Exception e) {
            return APIResponse.<InvoiceDto>builder()
                    .status(APIStatus.Error)
                    .message(e.getMessage())
                    .build();
        }
    }

}
