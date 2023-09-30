package ru.geekbrains.gym.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.model.User;

import java.util.List;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { RoleMapper.class, PaidPeriodMapper.class }
)
public interface UserMapper extends EntityMapper<User, UserFullDto> {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserFullDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toEntity(UserFullDto userFullDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "paidPeriod", target = "paidPeriod")
    UserWithPaidPeriodDto toDtoWithPaidPeriod(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "paidPeriod", target = "paidPeriod")
    List<UserWithPaidPeriodDto> toDtosWithPaidPeriod(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "paidPeriod", ignore = true)
    @Mapping(target = "roles", ignore = true)
    void updateUserEntity(UserFullDto dto, @MappingTarget User user);

}
