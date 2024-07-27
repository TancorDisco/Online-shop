package ru.online_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.online_shop.models.Person;
import ru.online_shop.services.PersonService;
import ru.online_shop.services.ProductService;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final PersonService personService;

    @Autowired
    public CartController(ProductService productService, PersonService personService) {
        this.productService = productService;
        this.personService = personService;
    }

    @GetMapping
    public String cartPage(Model model) {
        Person authUser = getAuthUser();
        model.addAttribute("productsOfUser", productService.findByPerson(authUser));
        model.addAttribute("totalPrice", productService.getTotalPrice(authUser));
        return "cart/cart-main";
    }

    @DeleteMapping("/remove-from-the-cart/{id}")
    public String removeFromTheCart(@PathVariable Long id) {
        personService.removeFromTheCart(getAuthUser(), productService.findById(id));
        return "redirect:/cart";
    }

    public Person getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = personService.findByUsername(auth.getName());
        return person.orElse(null);
    }
}
