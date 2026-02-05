package net.abbas.dataaccess.entity.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.order.Invoice;
import net.abbas.dataaccess.entity.user.User;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long amount;

    @ManyToOne
    private Invoice invoice;

    @ManyToOne
    private User customer;

    private String authority;
    private String code;
    private String verifyCode;
    private String description;
    private String resultMessage;
    private String verifyMessage;
    private String cardHash;
    private String cardPan;
    private String refId;

    @ManyToOne
    private Payment payment;

}
