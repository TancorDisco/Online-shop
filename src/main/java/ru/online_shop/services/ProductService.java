package ru.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.online_shop.models.Product;
import ru.online_shop.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByTitleStartingWith(String request) {
        return productRepository.findByTitleStartingWith(request);
    }

    public List<Product> findProducts(String request) {
        if (request == null) {
            return productRepository.findAll();
        } else {
            return productRepository.findByTitleStartingWith(request);
        }
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }
}
