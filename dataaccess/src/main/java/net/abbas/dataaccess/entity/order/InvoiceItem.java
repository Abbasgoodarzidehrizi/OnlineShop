package net.abbas.dataaccess.entity.order;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.product.Color;
import net.abbas.dataaccess.entity.product.Product;
import net.abbas.dataaccess.entity.product.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Color color;

    @ManyToOne
    private Size size;

    private Integer quantity;
    private Long price;
}
