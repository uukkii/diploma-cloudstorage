package ru.netology.diplomacloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.netology.diplomacloudstorage.model.User;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
