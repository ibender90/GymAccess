package ru.geekbrains.gym.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserFullDto extends UserMinimalDto{

    private PaidPeriodDto paidPeriod;
    private Set<RoleDto> roles;

}
