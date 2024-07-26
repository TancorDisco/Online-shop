package ru.online_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.online_shop.models.Person;
import ru.online_shop.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findById(Long id);

    List<Product> findByTitleStartingWith(String request);

    void deleteById(Long id);

    List<Product> findAllByPeople(Person person);

}
