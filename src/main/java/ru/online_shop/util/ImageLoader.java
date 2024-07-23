package ru.online_shop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.online_shop.models.Image;
import ru.online_shop.services.ImageService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Component
public class ImageLoader /*implements CommandLineRunner*/ {

    /*private final ImageService imageService;

    @Autowired
    public ImageLoader(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    public void run(String... args) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        Path path = Paths.get(Objects.requireNonNull(classLoader.getResource("static/images/user.png")).toURI());
        byte[] bytes = Files.readAllBytes(path);
        String originalFileName = path.getFileName().toString();
        String contentType = Files.probeContentType(path);
        long size = bytes.length;

        Image image = new Image();
        image.setName(originalFileName);
        image.setOriginalFileName(originalFileName);
        image.setContentType(contentType);
        image.setSize(size);
        image.setBytes(bytes);

        imageService.save(image);
    }*/
}
