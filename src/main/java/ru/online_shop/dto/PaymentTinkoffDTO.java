package ru.online_shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.online_shop.models.Person;

@Data
public class PaymentTinkoffDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("from")
    private PaymentFromDTO from;

    @JsonProperty("to")
    private PaymentToDTO to;

    @JsonProperty("purpose")
    private String purpose;

    @JsonProperty("amount")
    private Double amount;
}
