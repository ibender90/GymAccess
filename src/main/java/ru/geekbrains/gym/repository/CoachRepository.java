package ru.geekbrains.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.gym.model.Coach;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByEmail(String coachEmail);
}
