package ru.online_shop.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.online_shop.dto.ProductDTO;
import ru.online_shop.models.Product;
import ru.online_shop.services.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
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

    @GetMapping("/{id}/update")
    public String updatePage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productService.findById(id));
        return "/products/update";
    }

    @PatchMapping("/{id}/update")
    public String update (@ModelAttribute("product") @Valid ProductDTO productDTO,
                          BindingResult bindingResult, @PathVariable("id") Long id) {
        Product product = convertToProduct(productDTO);

        if (bindingResult.hasErrors()) {
            return "/products/update";
        }

        productService.update(product, id);
        return "redirect:/products/{id}";
    }

    public Product convertToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
