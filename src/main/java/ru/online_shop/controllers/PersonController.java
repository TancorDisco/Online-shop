package ru.online_shop.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.online_shop.dto.PersonDTO;
import ru.online_shop.models.Person;
import ru.online_shop.services.PersonService;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PersonController {

    private final ModelMapper modelMapper;
    private final PersonService personService;

    @Autowired
    public PersonController(ModelMapper modelMapper, PersonService personService) {
        this.modelMapper = modelMapper;
        this.personService = personService;
    }

    @GetMapping
    public String accountPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("person", personService.findByUsername(username).get());
        return "user/account";
    }

    @GetMapping("/change")
    public String changeProfile(@ModelAttribute("person") PersonDTO personDTO) {
        return "user/change-info";
    }

    //TODO do correct change username & password
    @PatchMapping("/change")
    public String updateProfile(@ModelAttribute("person") @Valid PersonDTO personDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/change";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> personToBeUpdated = personService.findByUsername(auth.getName());
        if (personToBeUpdated.isPresent()) {
            Person person = convertToPerson(personDTO);
            personService.update(personToBeUpdated.get(), person);
        } else {
            throw new UsernameNotFoundException("Ошибка!");
        }
        return "redirect:/user";
    }


    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }
}
