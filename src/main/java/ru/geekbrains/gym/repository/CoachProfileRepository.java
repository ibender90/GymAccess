package ru.geekbrains.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.gym.model.CoachProfile;

public interface CoachProfileRepository extends JpaRepository<CoachProfile, Long> {
}
