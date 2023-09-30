package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.dto.PaidPeriodDto;

import java.text.SimpleDateFormat;

public class PaidPeriodDtoMock {
    public static PaidPeriodDto getPaidPeriodDtoMock(){
        return new PaidPeriodDto(1L,"12-12-2023", "12-12-2024" );
    }
}
