package ru.online_shop.services;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.online_shop.models.Image;
import ru.online_shop.models.Product;
import ru.online_shop.repositories.ProductRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
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

    @Transactional
    public void createProduct(Product product, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            images.add(convertToImage(file));
        }
        if (!images.isEmpty()) {
            for (Image image : images) {
                image.setProduct(product);
            }
            images.getFirst().setPreviewImage(true);
            product.setImages(images);
            product.setPreviewImageId(images.getFirst().getId());
        }
        productRepository.save(product);
    }

    @Transactional
    public void update(Product product, Long id) {
        product.setId(id);
        product.setDateOfCreated(LocalDateTime.now());
        productRepository.save(product);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @SneakyThrows
    private Image convertToImage(MultipartFile file) {
        return modelMapper.map(file, Image.class);
    }
}
