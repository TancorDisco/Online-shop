package ru.online_shop.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.online_shop.dto.ProductDTO;
import ru.online_shop.dto.ReviewDTO;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.services.PersonService;
import ru.online_shop.services.ProductService;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final PersonService personService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, PersonService personService, ModelMapper modelMapper) {
        this.productService = productService;
        this.personService = personService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String index(Model model, @RequestParam(name = "searchRequest", required = false) String request) {
        model.addAttribute("products", productService.findProducts(request));
        return "/products/index";
    }

    @GetMapping("/{id}")
    public String showProductInfo(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        model.addAttribute("review", new ReviewDTO());
        return "/products/product-info";
    }

    @GetMapping("/{id}/update")
    public String updatePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productService.findById(id));
        return "/products/update";
    }

    @PatchMapping("/{id}/update")
    public String update(@ModelAttribute("product") @Valid ProductDTO productDTO,
                          BindingResult bindingResult, @PathVariable("id") Long id) {
        Product product = convertToProduct(productDTO);

        if (bindingResult.hasErrors()) {
            return "/products/update";
        }

        productService.update(product, id);
        return "redirect:/products/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }

    @GetMapping("/{id}/add-product-to-cart")
    public String addProductToCart(@PathVariable Long id) {
        personService.addProductToCart(getAuthUser(), productService.findById(id));
        return "redirect:/products/{id}";
    }


    public Product convertToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public Person getAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Person> person = personService.findByUsername(auth.getName());
        return person.orElse(null);
    }
}
