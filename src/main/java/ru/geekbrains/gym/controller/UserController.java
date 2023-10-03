package ru.geekbrains.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
public class UserController {
    private final UserService userService;

    @Operation(
            description = "Get endpoint for user to see his personal info",
            summary = "After login user can see his personal information on the main page",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    @GetMapping(value = "/", produces = {"application/json"})
    public ResponseEntity<UserWithPaidPeriodDto> getUserByToken(Authentication authentication) {
        //log.debug("REST request to get User : {}", id);
        UserWithPaidPeriodDto userFound = userService.findUserByEmail(authentication.getName());
        return ResponseEntity
                .ok()
                .body(userFound);
    }
}
