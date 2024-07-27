package ru.online_shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentFromDTO {

    @JsonProperty("accountNumber")
    private String accountNumber;
}
