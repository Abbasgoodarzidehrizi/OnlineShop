package net.abbas.dataaccess.entity.order;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.user.User;
import net.abbas.dataaccess.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createDate;
    private LocalDate payedDate;
    private OrderStatus status;
    private Long totalAmount;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceItem> invoiceItem;


}
