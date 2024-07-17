package ru.online_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.online_shop.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(Long id);
}
