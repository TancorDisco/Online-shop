package ru.online_shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.online_shop.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(name = "searchRequest", required = false) String request) {
        model.addAttribute("products", productService.findProducts(request));
        return "/products/index";
    }

    @GetMapping("/{id}")
    public String showProductInfo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        return "/products/product-info";
    }
}
