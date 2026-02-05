package net.abbas.dto.payment;

import jakarta.persistence.*;
import lombok.*;
import net.abbas.dataaccess.entity.user.Customer;
import net.abbas.dataaccess.entity.user.Role;
import net.abbas.enums.PaymentGateway;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoToPaymentDto {
    private String username;
    private String mobile;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String tel;
    private String address;
    private String postalCode;
    private List<BasketItem> basketItem;
    private PaymentGateway gateway;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasketItem{
        private Long productId;
        private Long colorId;
        private Long sizeId;
        private Integer quantity;
    }
}
