package ru.geekbrains.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.gym.model.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
