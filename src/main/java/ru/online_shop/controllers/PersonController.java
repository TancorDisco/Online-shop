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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.online_shop.dto.PersonDTO;
import ru.online_shop.models.Image;
import ru.online_shop.models.Person;
import ru.online_shop.repositories.ImageRepository;
import ru.online_shop.services.ImageService;
import ru.online_shop.services.PersonService;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class PersonController {

    private final ModelMapper modelMapper;
    private final PersonService personService;
    private final ImageService imageService;

    @Autowired
    public PersonController(ModelMapper modelMapper, PersonService personService, ImageService imageService) {
        this.modelMapper = modelMapper;
        this.personService = personService;
        this.imageService = imageService;
    }

    @GetMapping
    public String accountPage(Model model) {
        Person authUser = getAuthUser();
        model.addAttribute("person", authUser);
        model.addAttribute("profilePicture", imageService.findById(authUser.getProfilePictureId())
                .orElse(null));
        return "user/account";
    }

    @GetMapping("/change")
    public String changeProfile(Model model) {
        model.addAttribute("person", getAuthUser());
        return "user/change-info";
    }

    //TODO do correct change username & password
    @PatchMapping("/change")
    public String updateProfile(@ModelAttribute("person") @Valid PersonDTO personDTO,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user/change";
        }
        Person person = convertToPerson(personDTO);
        personService.update(getAuthUser(), person);
        return "redirect:/user";
    }

    @PostMapping("/add-profile-picture")
    public String addProfilePicture(@RequestParam("profileImage") MultipartFile image) {
        if (image.isEmpty()) {
            return "user";
        }
        personService.addProfilePicture(getAuthUser(), image);
        return "redirect:/user";
    }


    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public Person getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = personService.findByUsername(auth.getName());
        if (person.isPresent()) {
            return person.get();
        } else {
            throw new UsernameNotFoundException("Ошибка");
        }
    }
}
