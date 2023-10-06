package ru.geekbrains.gym.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PermissionType {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    MANAGER_READ("manager:read"),
    MANAGER_UPDATE("manager:update"),
    MANAGER_CREATE("manager:create"),
    MANAGER_DELETE("manager:delete"),

    COACH_CREATE("coach_create"),
    COACH_READ("coach_read"),
    COACH_DELETE("coach_delete"),
    COACH_UPDATE("coach_update"),


    USER_READ("user:read")
    ;

    @Getter
    private final String permission;
}
