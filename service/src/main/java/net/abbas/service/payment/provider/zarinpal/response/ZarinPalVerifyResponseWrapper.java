package net.abbas.service.payment.provider.zarinpal.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZarinPalVerifyResponseWrapper {
    private ZarinPalVerifyResponse data;
    private Object errors;

}
