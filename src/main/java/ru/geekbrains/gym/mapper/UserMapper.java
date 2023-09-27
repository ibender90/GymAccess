package ru.geekbrains.gym.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.geekbrains.gym.dto.UserDto;
import ru.geekbrains.gym.model.User;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { RoleMapper.class, PaidPeriodMapper.class }
)
public interface UserMapper extends EntityMapper<User, UserDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toEntity(UserDto userDto);

}
