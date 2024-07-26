package ru.online_shop.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.online_shop.models.Image;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {

    private Long id;

    @NotEmpty(message = "Незаполненное поле!")
    @Size(min = 2, max = 30, message = "Название должно быть от 2 до 30 символов")
    private String title;

    private String description;

    @NotNull(message = "Незаполненное поле!")
    private Double price;

    private String category;

    @Min(0)
    private Integer quantity;
}
