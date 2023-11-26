package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.dto.RoleDto;
import ru.geekbrains.gym.dto.UserFullDto;

import java.util.HashSet;
import java.util.Set;

public class UserFullDtoMock {
    public static UserFullDto getMock(Long id, PaidPeriodDto paidPeriodDto, RoleDto roleDto){
        return UserFullDto.builder()
                .id(id)
                .firstName("MockFirstName")
                .lastName("MockLastName")
                .email("MockEmail")
                .paidPeriod(paidPeriodDto)
                .roles(new HashSet<>(Set.of(roleDto)))
                .build();
    }
}
