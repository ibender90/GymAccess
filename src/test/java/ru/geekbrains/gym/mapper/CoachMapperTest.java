package ru.geekbrains.gym.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.geekbrains.gym.dto.CoachDto;
import ru.geekbrains.gym.dto.CoachProfileDto;
import ru.geekbrains.gym.dto.WorkoutDto;
import ru.geekbrains.gym.mocks.CoachDtoMock;
import ru.geekbrains.gym.mocks.CoachProfileDtoMock;
import ru.geekbrains.gym.mocks.WorkoutDtoMock;
import ru.geekbrains.gym.model.Coach;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CoachMapperTest {
    @Spy
    private CoachMapper coachMapper = new CoachMapperImpl();

    @BeforeEach
    public void initMapperDependencies() {
        WorkoutMapper workoutMapper = new WorkoutMapperImpl();
        CoachProfileMapper coachProfileMapper = new CoachProfileMapperImpl();
        ReflectionTestUtils.setField(coachMapper, "workoutMapper", workoutMapper);
        ReflectionTestUtils.setField(coachMapper, "coachProfileMapper", coachProfileMapper);
    }

    @Test
    @DisplayName("Success mapping coachDto to Entity")
    public void testMappingFromDtoToEntity(){
        CoachProfileDto mockProfile = CoachProfileDtoMock.getMock(2L);
        List<WorkoutDto> mockWorkouts = List.of(WorkoutDtoMock.getMock(5L, 5L)); //shall I ignore workouts?
        CoachDto dto = CoachDtoMock.getMock(5L,mockWorkouts, mockProfile);
        Coach entity = coachMapper.toEntity(dto);
        System.out.println(entity);
    }
}
