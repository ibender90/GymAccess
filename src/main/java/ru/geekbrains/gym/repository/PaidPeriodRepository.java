package ru.geekbrains.gym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.gym.model.PaidPeriod;

public interface PaidPeriodRepository extends JpaRepository<PaidPeriod, Long> {
}
