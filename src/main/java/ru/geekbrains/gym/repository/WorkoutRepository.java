package ru.geekbrains.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.gym.model.Workout;

import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Optional<List<Workout>> findAllByCoach(Long coachID);
}
