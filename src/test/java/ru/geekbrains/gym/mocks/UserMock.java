package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.model.PaidPeriod;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.Token;
import ru.geekbrains.gym.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserMock {
    public static User getUserMock(Long id, PaidPeriod paidPeriod, Role role, Token token){
        return User.builder()
                .id(id)
                .paidPeriod(paidPeriod)
                .roles(new HashSet<>(Set.of(role)))
                .email("mock@email.com")
                .password("mockPassword")
                .firstName("MockFirstName")
                .lastName("MockLastName")
                .tokens(List.of(token))
                .build();
    }
}
