package ru.geekbrains.gym.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.geekbrains.gym.dto.RoleDto;
import ru.geekbrains.gym.model.Role;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends EntityMapper<Role, RoleDto>{
}
