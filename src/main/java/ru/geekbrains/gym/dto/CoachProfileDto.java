package ru.geekbrains.gym.dto;

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
    private String personalInfo;
    private String linkToPhoto;
}
