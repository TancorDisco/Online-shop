package ru.online_shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.online_shop.models.Image;
import ru.online_shop.repositories.ImageRepository;

import java.util.Optional;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    public void save(Image image) {
        imageRepository.save(image);
    }
}
