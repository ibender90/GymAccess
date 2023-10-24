package ru.geekbrains.gym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.geekbrains.gym.dto.CoachProfileDto;
import ru.geekbrains.gym.model.CoachProfile;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CoachProfileMapper extends EntityMapper<CoachProfile, CoachProfileDto>{
    @Override
    @Mapping(source = "entity.coach.id", target = "coachId")
    CoachProfileDto toDto(CoachProfile entity);
}
