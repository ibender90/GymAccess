package ru.geekbrains.gym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.model.PaidPeriod;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaidPeriodMapper extends EntityMapper<PaidPeriod, PaidPeriodDto>{
}
