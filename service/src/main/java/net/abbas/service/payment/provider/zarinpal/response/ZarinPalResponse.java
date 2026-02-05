package net.abbas.service.payment.provider.zarinpal.response;

import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZarinPalResponse {
    private String code;
    private String message;
    private String authority;
    private String fee_type;
    private Integer fee;

}
