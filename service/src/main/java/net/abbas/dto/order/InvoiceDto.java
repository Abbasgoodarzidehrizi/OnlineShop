package net.abbas.dto.order;

import lombok.*;
import net.abbas.dataaccess.entity.order.InvoiceItem;
import net.abbas.dataaccess.enums.OrderStatus;
import net.abbas.dto.user.LimitedUserDto;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private Long id;
    private LocalDate createDate;
    private LocalDate payedDate;
    private OrderStatus status;
    private Long totalAmount;
    private List<InvoiceItemDto> invoiceItem;
    private LimitedUserDto user;
}
