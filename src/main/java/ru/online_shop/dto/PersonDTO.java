package ru.online_shop.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PersonDTO {

    @NotEmpty(message = "Незаполненное поле!")
    private String username;

    @NotEmpty(message = "Незаполненное поле!")
    private String password;

    @NotEmpty(message = "Незаполненное поле!")
    private String name;

    @NotEmpty(message = "Незаполненное поле!")
    private String surname;

    @NotEmpty(message = "Незаполненное поле!")
    private String patronymic;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    @Email
    @NotEmpty(message = "Незаполненное поле!")
    private String email;

    @NotEmpty(message = "Незаполненное поле!")
    /*@Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Ваш адрес должен быть в формате: " +
            "страна, город, индекс(6 цифр) Пример: Россия, Москва, 123456")*/
    private String address;

    private Long accountNumber;

    private Long inn;

    private Long bik;
}
