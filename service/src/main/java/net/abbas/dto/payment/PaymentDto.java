package net.abbas.dto.payment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Long Id;
    private String name;
    private String description;
}
