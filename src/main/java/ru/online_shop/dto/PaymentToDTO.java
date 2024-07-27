package ru.online_shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentToDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("inn")
    private String inn;

    @JsonProperty("bik")
    private String bik;

    @JsonProperty("accountNumber")
    private String accountNumber;
}
