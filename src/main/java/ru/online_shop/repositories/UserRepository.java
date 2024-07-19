package ru.online_shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.online_shop.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
