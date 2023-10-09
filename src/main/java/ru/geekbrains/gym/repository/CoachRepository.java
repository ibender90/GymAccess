package ru.geekbrains.gym.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.gym.model.Coach;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findByEmail(String coachEmail);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO coach (id) values (:id)", nativeQuery = true)
    void joinCoachTable(@Param("id") Long id);
}
