package ru.geekbrains.gym.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.gym.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

}
