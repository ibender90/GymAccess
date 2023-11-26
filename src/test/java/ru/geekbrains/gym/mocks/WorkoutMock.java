package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class WorkoutMock {
    public static Workout getMock(Long id, Coach coach) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
        return Workout.builder()
                .id(id)
                .title("workoutMockTitle")
                .description("workoutMockDescription")
                .date(format.parse("20-02-2022 01:02:03"))
                .coach(coach)
                .freeSpace(10)
                .build();
    }
}
