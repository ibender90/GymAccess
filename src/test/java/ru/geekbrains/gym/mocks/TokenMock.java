package ru.geekbrains.gym.mocks;

import ru.geekbrains.gym.enums.TokenType;
import ru.geekbrains.gym.model.Token;

public class TokenMock {
    public static Token getTokenMock() {
        return Token.builder()
                .id(1L)
                .tokenType(TokenType.BEARER)
                .token("MockToken")
                .user(null)
                .expired(false)
                .revoked(false)
                .build();
    }
}
