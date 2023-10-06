package ru.geekbrains.gym.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkoutDto {
    private Long id;
    private String title;
    private String description;
    private String date;
    private Long coachId;
    private Integer freeSpace;
}
