package ru.geekbrains.gym.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.gym.validator.FutureDate;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutDto {
    private Long id;
    @NotBlank(message = "Workout title can't be empty")
    private String title;
    @NotBlank(message = "Description can't be empty")
    private String description;
    @NotNull(message = "Please specify the workout date")
    @FutureDate
    private String date;
    private Long coachId;
    @NotNull(message = "Please specify maximum space")
    @Min(value = 1, message = "Free space can't be less than 1")
    @Max(value = 200, message = "Free space is not within reasonable amount of people")
    private Integer freeSpace;
}
