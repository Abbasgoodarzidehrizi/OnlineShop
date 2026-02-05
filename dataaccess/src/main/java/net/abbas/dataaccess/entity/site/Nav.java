package net.abbas.dataaccess.entity.site;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.order.InvoiceItem;
import net.abbas.dataaccess.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nav {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 1000, nullable = false)
    private String link;

    @Column(nullable = false)
    private Boolean enabled = true;

    private Integer orderNumber;


}
