package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.dto.WorkoutDto;

public class WorkoutDtoMock {
    public static WorkoutDto getMock(Long id, Long coachId){
        return WorkoutDto.builder()
                .id(id)
                .date("12-12-2012 00:00:00")
                .title("MockWorkout")
                .description("MockDescription")
                .freeSpace(10)
                .coachId(coachId)
                .build();
    }
}
