package ru.online_shop.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.online_shop.dto.PaymentFromDTO;
import ru.online_shop.dto.PaymentTinkoffDTO;
import ru.online_shop.dto.PaymentToDTO;
import ru.online_shop.models.Person;

@Slf4j
@Service
public class PaymentTinkoffService {

    private static final String TINKOFF_TOKEN = "TinkoffOpenApiSandboxSecretToken";
    private final ProductService productService;
    private final RestTemplate restTemplate;

    @Autowired
    public PaymentTinkoffService(ProductService productService, RestTemplate restTemplate) {
        this.productService = productService;
        this.restTemplate = restTemplate;
    }

    public boolean processPayment(Person person) {
        PaymentTinkoffDTO paymentRequest = new PaymentTinkoffDTO();
        String id = String.valueOf(person.getId());
        paymentRequest.setId(id);

        PaymentFromDTO from = new PaymentFromDTO();
        from.setAccountNumber(person.getAccountNumber());
        paymentRequest.setFrom(from);

        PaymentToDTO to = new PaymentToDTO();
        to.setName("Семёнов С.А.");
        to.setInn("0");
        to.setBik("044525974");
        to.setAccountNumber("11122233344455566677");
        paymentRequest.setTo(to);

        paymentRequest.setPurpose("Оплата товаров");
        paymentRequest.setAmount(productService.getTotalPrice(person));

        if (String.valueOf(processRequestPayment(paymentRequest)).equals("201 CREATED") ) {
            return getStatusPayment(id) == HttpStatus.OK;
        }

        return false;
    }

    private HttpStatusCode processRequestPayment(PaymentTinkoffDTO paymentRequest) {
        final String url = "https://business.tbank.ru/openapi/sandbox/secured/api/v1/payment/ruble-transfer/pay";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + TINKOFF_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentTinkoffDTO> request = new HttpEntity<>(paymentRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        log.info("StatusSendPayment: {}", String.valueOf(response.getStatusCode()));

        return response.getStatusCode();
    }

    private HttpStatusCode getStatusPayment(String id) {
        String url = "https://business.tbank.ru/openapi/sandbox/secured/api/v1/payment/" + id;

        HttpHeaders headers = new HttpHeaders();

        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + TINKOFF_TOKEN);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        log.info("StatusPayment: {}", String.valueOf(response.getStatusCode()));

        return response.getStatusCode();
    }
}
