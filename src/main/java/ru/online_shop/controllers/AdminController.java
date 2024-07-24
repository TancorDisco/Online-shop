package ru.online_shop.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.online_shop.dto.PersonDTO;
import ru.online_shop.dto.ProductDTO;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.services.ProductService;

import java.util.List;

@Slf4j
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
                                @RequestParam(name = "images", required = false) List<MultipartFile> images,
                                BindingResult bindingResult) {
        log.info("Received request to create product: {}", productDTO);
        log.info("Received images: {}", images);

        if (bindingResult.hasErrors()) {
            return "admin/create-product";
        }
        Product product = convertToPerson(productDTO);
        productService.createProduct(product, images);

        return "redirect:/products";
    }

    @GetMapping("select-product")
    public String selectProductToBeUpdated(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/select-product";
    }

    public Product convertToPerson(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
