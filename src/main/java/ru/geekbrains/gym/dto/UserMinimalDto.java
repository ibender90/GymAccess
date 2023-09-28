package ru.geekbrains.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserMinimalDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
