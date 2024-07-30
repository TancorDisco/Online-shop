package ru.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;
import ru.online_shop.models.Review;
import ru.online_shop.repositories.ReviewRepository;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PersonService personService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, PersonService personService) {
        this.reviewRepository = reviewRepository;
        this.personService = personService;
    }

    public void addReview(Review review, Product product) {
        review.setProduct(product);
        product.getReviews().add(review);

        Person authUser = getAuthUser();
        review.setPerson(authUser);
        authUser.getReviews().add(review);
        reviewRepository.save(review);
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
