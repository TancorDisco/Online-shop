package ru.online_shop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO в одном заказе несколько товаров
    @OneToOne
    @JoinColumn(name = "product", referencedColumnName = "id")
    private Product product;

    @Column(name = "status")
    @NotEmpty
    private String status;

    @Column(name = "price")
    @NotNull
    private Double price;

    @Column(name = "delivery_info")
    @NotEmpty
    private String deliveryInfo;
}
