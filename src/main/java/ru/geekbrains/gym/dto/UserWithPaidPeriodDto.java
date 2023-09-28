package ru.geekbrains.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserWithPaidPeriodDto extends UserMinimalDto {
    private PaidPeriodDto paidPeriodDto;
}
