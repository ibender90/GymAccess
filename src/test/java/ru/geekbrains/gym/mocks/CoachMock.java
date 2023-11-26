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
                .workouts(null)
                .coachProfile(null)
                .build();
    }
}
