package net.abbas.service.payment.provider.zarinpal.response;

import lombok.*;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZarinPalResponseWrapper {
    private ZarinPalResponse data;
    private Object[] errors;
}
