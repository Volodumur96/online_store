package ua.com.shop.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.shop.shop.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLoginEquals(String login);
}
