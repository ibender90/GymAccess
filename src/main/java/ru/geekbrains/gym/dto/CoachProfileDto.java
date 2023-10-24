package ru.geekbrains.gym.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoachProfileDto {
    private Long id;
    @NotBlank(message = "Personal info can't be empty")
    private String personalInfo;
    @NotBlank(message = "Link to photo can't be empty")
    private String linkToPhoto;

    private Long coachId;
}
