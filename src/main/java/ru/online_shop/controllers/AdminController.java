package ru.online_shop.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.online_shop.dto.PersonDTO;
import ru.online_shop.dto.ProductDTO;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.services.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public AdminController(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @GetMapping
    public String selectAction() {
        return "/admin/actions";
    }

    @GetMapping("/create-product")
    public String createProductPage(@ModelAttribute("product") ProductDTO productDTO) {
        return "admin/create-product";
    }

    @PostMapping("/create-product")
    public String createProduct(@ModelAttribute("product") @Valid ProductDTO productDTO,
                                BindingResult bindingResult) {
        Product product = convertToPerson(productDTO);

        if (bindingResult.hasErrors()) {
            return "admin/create-product";
        }

        productService.createProduct(product);

        return "redirect:/products";
    }

    public Product convertToPerson(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

}
