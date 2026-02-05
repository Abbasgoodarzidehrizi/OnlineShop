package net.abbas.dto.order;

import lombok.*;
import net.abbas.dto.product.ColorDto;
import net.abbas.dto.product.ProductDto;
import net.abbas.dto.product.SizeDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItemDto {
    private Long id;
    private ProductDto product;
    private ColorDto color;
    private SizeDto size;
    private Integer quantity;
    private Long price;
}
