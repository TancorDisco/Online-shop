package ru.online_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.online_shop.models.Person;
import ru.online_shop.services.PersonService;

import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final PersonService personService;

    @Autowired
    public GlobalControllerAdvice(PersonService personService) {
        this.personService = personService;
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        Person authUser = getAuthUser();
        if (authUser != null) {
            model.addAttribute("profilePictureId", authUser.getProfilePictureId());
        }
    }

    public Person getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = personService.findByUsername(auth.getName());
        return person.orElse(null);
    }
}
