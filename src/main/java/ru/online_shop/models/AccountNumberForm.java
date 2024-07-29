package ru.online_shop.models;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountNumberForm {

    @Pattern(regexp = "^(\\d{20}|\\d{22})$", message = "Счёт должен быть 20 или 22 цифры")
    private String accountNumber;
}
