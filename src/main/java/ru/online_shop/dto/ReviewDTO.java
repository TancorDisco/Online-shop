package ru.online_shop.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDTO {

    @NotEmpty
    private String text;

    @NotNull
    @Min(1)
    @Max(10)
    private Integer grade;
}
