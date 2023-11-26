package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.dto.CoachDto;
import ru.geekbrains.gym.dto.CoachProfileDto;
import ru.geekbrains.gym.dto.WorkoutDto;

import java.util.List;

public class CoachDtoMock {
    public static CoachDto getMock(Long id, List<WorkoutDto> workouts, CoachProfileDto profile){
        return CoachDto.builder()
                .id(id)
                .coachProfile(profile)
                .workouts(workouts)
                .build();
    }
}
