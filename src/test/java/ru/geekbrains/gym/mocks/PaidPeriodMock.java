package ru.geekbrains.gym.mocks;

import org.springframework.format.annotation.DateTimeFormat;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.model.PaidPeriod;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PaidPeriodMock {

    public static PaidPeriod getMockPaidPeriod(Long id) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);

        return PaidPeriod.builder()
                .id(id)
                .dateFrom(format.parse("20-01-1990 01:02:03"))
                .dateTo(format.parse("20-01-2030 01:02:03"))
                .build();
    }
}
