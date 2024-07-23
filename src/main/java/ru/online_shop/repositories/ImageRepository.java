package ru.online_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.online_shop.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
