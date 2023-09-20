package ru.geekbrains.gym.repository;

import org.springframework.data.repository.CrudRepository;
import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.model.Role;

import java.util.Optional;

public interface UserRoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleName roleName);
}
