package ru.geekbrains.gym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.model.PaidPeriod;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface PaidPeriodMapper extends EntityMapper<PaidPeriod, PaidPeriodDto>{

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.dateTo", target = "dateTo", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(source = "entity.dateFrom", target = "dateFrom", dateFormat = "dd-MM-yyyy HH:mm:ss")
    PaidPeriodDto toDto(PaidPeriod entity);

    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.dateTo", target = "dateTo", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(source = "dto.dateFrom", target = "dateFrom", dateFormat = "dd-MM-yyyy HH:mm:ss")
    PaidPeriod toEntity(PaidPeriodDto dto);


}
