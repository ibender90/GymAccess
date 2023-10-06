package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.Role;

import java.util.HashSet;
import java.util.Set;

public class CoachMock {
    public static Coach getMock(Long id){
        return Coach.builder()
                .id(id)
                .email("mockCoachEmail")
                .firstName("mockCoachName")
                .lastName("mockCoachLastName")
                .roles(new HashSet<>(Set.of(new Role(1L, RoleName.USER), new Role(2L, RoleName.COACH))))
                .paidPeriod(null)
                .workouts(null)
                .build();
    }
}
