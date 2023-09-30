package ru.geekbrains.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.service.UserService;

//user controller gives access to manager or admin to view userDto, to set paid period for access to gym
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class UserController {
    private final UserService userService;




}
