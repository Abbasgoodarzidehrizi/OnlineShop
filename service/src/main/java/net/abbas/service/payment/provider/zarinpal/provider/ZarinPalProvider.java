package net.abbas.service.payment.provider.zarinpal.provider;

import net.abbas.dataaccess.entity.payment.Transaction;
import net.abbas.service.payment.provider.zarinpal.client.ZarinPalClient;
import net.abbas.service.payment.provider.zarinpal.request.ZarinPalRequest;
import net.abbas.service.payment.provider.zarinpal.request.ZarinPalVerifyRequest;
import net.abbas.service.payment.provider.zarinpal.response.ZarinPalResponse;
import net.abbas.service.payment.provider.zarinpal.response.ZarinPalVerifyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ZarinPalProvider {
    @Value("${app.payment-gateway.zarinpal.merchant-id}")
    private String merchantId;

    @Value("${app.payment-gateway.zarinpal.callback-url}")
    private String callBackUrl;

    @Value("${app.payment-gateway.zarinpal.ipg-url}")
    private String ipgUrl;
    private final ZarinPalClient client;

    @Autowired
    public ZarinPalProvider(ZarinPalClient client) {
        this.client = client;
    }

    public String goToPayment(Transaction trx) {
        ZarinPalRequest request = ZarinPalRequest.builder()
                .merchant_id(merchantId)
                .callback_url(callBackUrl)
                .currency("IRT")
                .amount(trx.getAmount().intValue())
                .description(trx.getDescription())
                .metaData(ZarinPalRequest.MetaData.builder()
                        .email(trx.getCustomer() != null ? trx.getCustomer().getEmail() : "")
                        .mobile(trx.getCustomer() != null ? trx.getCustomer().getMobile() : "")
                        .order_id(trx.getInvoice() != null ? trx.getInvoice().getId().toString() : "")
                        .build())
                .build();
        ZarinPalResponse response = client.goToPayment(request);
        if (response != null) {
            trx.setAuthority(response.getAuthority());
            trx.setCode(response.getCode());
            trx.setResultMessage(response.getMessage());
        }
        assert response != null;
        return ipgUrl + response.getAuthority();
    }

    public Transaction verifyPayment(Transaction trx) {
        ZarinPalVerifyRequest request = ZarinPalVerifyRequest.builder()
                .authority(trx.getAuthority())
                .amount(trx.getAmount().intValue())
                .merchant_id(merchantId)
                .build();

        ZarinPalVerifyResponse response = client.verify(request);
        if (response != null) {
            trx.setVerifyCode(response.getCode());
            trx.setResultMessage(response.getMessage());
            trx.setCardHash(response.getCard_hash());
            trx.setCardPan(response.getCard_pan());
            trx.setRefId(response.getRef_id());
        }
        return trx;
    }
}
