package ru.geekbrains.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDto{

    private int page;
    private int size;
    private String sortingFields;
    private String sortDirection;
    private List<UserDto> data;

}
