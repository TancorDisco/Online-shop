package ru.online_shop.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.online_shop.dto.PersonDTO;
import ru.online_shop.models.Person;
import ru.online_shop.services.RegistrationService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final ModelMapper modelMapper;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(ModelMapper modelMapper, RegistrationService registrationService) {
        this.modelMapper = modelMapper;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") PersonDTO personDTO) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("person") @Valid PersonDTO personDTO,
                                      BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        registrationService.register(person);

        return "redirect:/auth/login";

    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
