package net.abbas.service.payment.provider.zarinpal.client;

import net.abbas.service.payment.provider.zarinpal.request.ZarinPalRequest;
import net.abbas.service.payment.provider.zarinpal.request.ZarinPalVerifyRequest;
import net.abbas.service.payment.provider.zarinpal.response.ZarinPalResponse;
import net.abbas.service.payment.provider.zarinpal.response.ZarinPalResponseWrapper;
import net.abbas.service.payment.provider.zarinpal.response.ZarinPalVerifyResponse;
import net.abbas.service.payment.provider.zarinpal.response.ZarinPalVerifyResponseWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class ZarinPalClient {

    @Value("${app.payment.gateway.zarinpal.base-url}")
    private String baseUrl;

    public ZarinPalResponse goToPayment(ZarinPalRequest request) {
        String url = baseUrl + "v4/payment/request.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ZarinPalRequest> requestEntity = new HttpEntity<>(request,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ZarinPalResponseWrapper> zarinPalResponseResponseEntity = restTemplate.postForEntity(url, requestEntity, ZarinPalResponseWrapper.class);
        return Objects.requireNonNull(zarinPalResponseResponseEntity.getBody()).getData();
    }

    public ZarinPalVerifyResponse verify(ZarinPalVerifyRequest request) {
        String url = baseUrl + "v4/payment/verify.json";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ZarinPalVerifyRequest> requestEntity = new HttpEntity<>(request,headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ZarinPalVerifyResponseWrapper> response = restTemplate.postForEntity(url, requestEntity, ZarinPalVerifyResponseWrapper.class);
        return Objects.requireNonNull(response.getBody()).getData();
    }
}
