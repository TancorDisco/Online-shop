package ru.online_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.online_shop.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
