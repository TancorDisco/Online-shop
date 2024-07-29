package ru.online_shop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Незаполненное поле!")
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "Незаполненное поле!")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "Незаполненное поле!")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Незаполненное поле!")
    private String surname;

    @Column(name = "patronymic")
    @NotEmpty(message = "Незаполненное поле!")
    private String patronymic;

    @Column(name = "birth_day")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Незаполненное поле!")
    private String email;

    @Column(name = "address")
    @NotEmpty(message = "Незаполненное поле!")
    /*@Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Ваш адрес должен быть в формате: " +
            "страна, город, индекс(6 цифр) Пример: Россия, Москва, 123456")*/
    private String address;

    @Column(name = "role")
    @NotEmpty
    private String role;

    @Column(name = "profile_picture_id")
    private Long profilePictureId;

    @ManyToMany
    @JoinTable(
            name = "person_product",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> productsInTheCart;

    @Column(name = "account_number")
    @Pattern(regexp = "^(\\d{20}|\\d{22})$", message = "Счёт должен быть 20 или 22 цифры")
    private String accountNumber;

    private Long inn;

    private Long bik;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Order> orders;

    /*@OneToMany(mappedBy = "user_entity")
    private List<Review> reviews;*/

    @Transient
    public String getFullName() {
        return name + " " + surname + " " + patronymic;
    }
}
