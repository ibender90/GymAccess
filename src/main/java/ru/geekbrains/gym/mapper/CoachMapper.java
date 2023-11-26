package ru.geekbrains.gym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.geekbrains.gym.dto.CoachDto;
import ru.geekbrains.gym.model.Coach;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        uses = {WorkoutMapper.class, CoachProfileMapper.class}
)
public interface CoachMapper extends EntityMapper<Coach, CoachDto>{
}
