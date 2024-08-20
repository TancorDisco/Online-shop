package ru.online_shop.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.online_shop.models.Product;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        Product product = Product.builder()
                .id(1L)
                .title("test")
                .price(100.0).build();
        Product product2 = Product.builder()
                .id(2L)
                .title("test2")
                .price(102.0).build();
        productRepository.save(product);
        productRepository.save(product2);
    }

    @Test
    public void findById_ShouldReturnProduct() {

        Product foundProduct = productRepository.findById(1L);

        assertThat(foundProduct).isNotNull();
    }

    @Test
    public void findById_ShouldReturnNothing() {

        Optional<Product> foundProduct = Optional.ofNullable(productRepository.findById(10L));

        assertThat(foundProduct).isEmpty();
    }

    @Test
    public void findByTitleStartingWith_ShouldReturnProduct() {
        List<Product> products = productRepository.findByTitleStartingWith("te");

        assertThat(products).isNotEmpty();
        assertThat(products).hasSize(2);
    }

    @Test
    public void deleteById_ShouldDeleteProduct() {
        productRepository.deleteById(2L);

        assertThat(productRepository.findById(2L)).isNull();
    }
}
