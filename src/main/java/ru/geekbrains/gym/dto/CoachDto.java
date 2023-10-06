package ru.geekbrains.gym.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class CoachDto extends UserMinimalDto{
    private List<WorkoutDto> workouts;
    private CoachProfileDto coachProfile;
}
