package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;

public class UserWithPaidPeriodDtoMock {

    public static UserWithPaidPeriodDto getMock(Long id, PaidPeriodDto paidPeriodDto){
        return UserWithPaidPeriodDto.builder()
                .id(id)
                .firstName("mockFirstName")
                .lastName("mockLastName")
                .email("mockEmail")
                .paidPeriod(paidPeriodDto)
                .build();
    }
}
