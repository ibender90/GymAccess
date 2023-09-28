package ru.geekbrains.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaidPeriodDto {

    private Long id;
    private String dateFrom;
    private String dateTo;
}
