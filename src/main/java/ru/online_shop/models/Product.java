package ru.online_shop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotEmpty(message = "Незаполненное поле!")
    @Size(min = 2, max = 30, message = "Название должно быть от 2 до 30 символов")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Незаполненное поле!")
    private Double price;

    @Column(name = "category")
    private String category;

    @Column(name = "quantity")
    @Min(0)
    private Integer quantity;
}
