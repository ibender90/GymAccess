package ru.geekbrains.gym.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.geekbrains.gym.model.Coach;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO coach (id) values (:id)", nativeQuery = true)
    void joinCoachTable(@Param("id") Long id);

    @Query(
            "SELECT c FROM Coach c where c.id = " +
                    "(SELECT u.id FROM User u where u.email LIKE :mail)"
    )
    Optional<Coach> findByEmail(@Param("mail") String email);
}
