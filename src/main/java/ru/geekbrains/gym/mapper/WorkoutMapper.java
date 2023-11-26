package ru.geekbrains.gym.mapper;

import org.mapstruct.*;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.dto.WorkoutDto;
import ru.geekbrains.gym.model.Workout;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface WorkoutMapper extends EntityMapper<Workout, WorkoutDto>{

    @Override
    @Mapping(source = "dto.date", target = "date", dateFormat = Constant.DATE_FORMAT)
    Workout toEntity(WorkoutDto dto);

    @Override
    @Mapping(source = "entity.date", target = "date", dateFormat = Constant.DATE_FORMAT)
    @Mapping(source = "entity.coach.id", target = "coachId")
    WorkoutDto toDto(Workout entity);

    @Override
    @Mapping(source = "dto.date", target = "date", dateFormat = Constant.DATE_FORMAT)
    List<WorkoutDto> toDtos(List<Workout> entityList);

    @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
    @Mapping(source = "dto.date", target = "date", dateFormat = Constant.DATE_FORMAT)
    List<Workout> toEntities(List<WorkoutDto> workoutDtos);
}
