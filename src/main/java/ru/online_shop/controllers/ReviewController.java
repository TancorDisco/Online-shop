package ru.online_shop.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.online_shop.dto.ReviewDTO;
import ru.online_shop.models.Product;
import ru.online_shop.models.Review;
import ru.online_shop.services.ProductService;
import ru.online_shop.services.ReviewService;

@Controller
public class ReviewController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ProductService productService, ModelMapper modelMapper, ReviewService reviewService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.reviewService = reviewService;
    }

    @PostMapping("/products/{id}/add-review")
    public String addReview(@PathVariable("id") Long id, @ModelAttribute("review-new") @Valid ReviewDTO reviewDTO,
                            Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Product product = productService.findById(id);
            model.addAttribute("product", product);
            model.addAttribute("images", product.getImages());
            model.addAttribute("review", new ReviewDTO());
            return "/products/product-info";
        }
        Review review = convertToReview(reviewDTO);
        reviewService.addReview(review, productService.findById(id));
        return "redirect:/products/" + id;
    }

    public Review convertToReview(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }
}
