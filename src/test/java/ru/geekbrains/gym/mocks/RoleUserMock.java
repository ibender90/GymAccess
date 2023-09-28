package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.model.Role;

public class RoleUserMock {
    public static Role getUserRoleMock(){
        return Role.builder()
                .id(1L)
                .roleName(RoleName.USER)
                .build();
    }
}
