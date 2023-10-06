package ru.geekbrains.gym.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.dto.WorkoutDto;
import ru.geekbrains.gym.mocks.CoachMock;
import ru.geekbrains.gym.mocks.WorkoutDtoMock;
import ru.geekbrains.gym.mocks.WorkoutMock;
import ru.geekbrains.gym.model.Coach;
import ru.geekbrains.gym.model.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class WorkoutMapperTest {

    @Spy
    private WorkoutMapper workoutMapper = new WorkoutMapperImpl();

    private final SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);


    @Test
    @DisplayName("Success mapping workoutDto to Entity")
    public void testMapToEntity() throws ParseException {
        WorkoutDto dto = WorkoutDtoMock.getMock(5L, 3L);
        Workout workout = workoutMapper.toEntity(dto);

        Date formattedDate = format.parse(dto.getDate());

        Assertions.assertNull(workout.getCoach()); //coach will be bonded
        Assertions.assertEquals(dto.getDescription(), workout.getDescription());
        Assertions.assertEquals(dto.getTitle(), workout.getTitle());
        Assertions.assertEquals(dto.getFreeSpace(), workout.getFreeSpace());
        Assertions.assertEquals(formattedDate, workout.getDate());

        System.out.println(workout);
    }

    @Test
    @DisplayName("Success mapping workout Entity to Dto")
    public void testMapToDto() throws ParseException {
        Coach coach = CoachMock.getMock(2L);
        Workout workout = WorkoutMock.getMock(3L, coach);
        WorkoutDto dto = workoutMapper.toDto(workout);

        String parsedDate = format.format(workout.getDate());
        Assertions.assertEquals(coach.getId(), dto.getCoachId());
        Assertions.assertEquals(parsedDate, dto.getDate());
        Assertions.assertEquals(workout.getTitle(), dto.getTitle());
        Assertions.assertEquals(workout.getDescription(), dto.getDescription());
        Assertions.assertEquals(workout.getFreeSpace(), dto.getFreeSpace());
    }
}
