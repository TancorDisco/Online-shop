package ru.online_shop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "User")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Ваш адрес должен быть в формате: " +
            "страна, город, индекс(6 цифр) Пример: Россия, Москва, 123456")
    private String address;

    @Column(name = "role")
    @NotEmpty
    private String role;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
