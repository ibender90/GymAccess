package ru.geekbrains.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/manager")
@Tag(name = "Manager")
@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
@RequiredArgsConstructor
public class ManagerController {
    private final UserService userService;

    @Operation(
            description = "Get endpoint for manager with parameter object",
            summary = "Manager searches for users by name/surname/email",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<UserWithPaidPeriodDto>> getUsersWithPaidPeriod(
            @ParameterObject UserSearchDto searchDto) {
        PaginatedResponseDto<UserWithPaidPeriodDto> paginatedResponse = userService.searchForUserAndPaidPeriod(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }


    @Operation(
            description = "Patch endpoint for manager",
            summary = "Update payment for selected user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"),
                    @ApiResponse(
                            description = "Incorrect paid period",
                            responseCode = "400"
                    )
            })
    @PutMapping(value = "/update_payment", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<UserWithPaidPeriodDto> updatePayment(
            @ParameterObject UserWithPaidPeriodDto userWithPaidPeriodDto) {
        UserWithPaidPeriodDto updatedUser = userService.editPaidPeriod(userWithPaidPeriodDto);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }

    @Operation(
            description = "Get endpoint for manager with path variable",
            summary = "View user by id to check his personal data and paid period",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<UserWithPaidPeriodDto> getUserById(@PathVariable(value = "id") final Long id) {
        //log.debug("REST request to get User : {}", id);
        UserWithPaidPeriodDto userFound = userService.findUserWithPaidPeriod(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }

    @Operation(
            description = "Get endpoint for manager to assign a coach",
            summary = "Selected user with get a role coach",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"),
                    @ApiResponse(
                            description = "User with this id is already a coach",
                            responseCode = "400"
                    )
            })
    @GetMapping(value = "/assign_coach/{id}", produces = {"application/json"})
    public ResponseEntity<UserFullDto> assignCoach(@PathVariable(value = "id") final Long id) {
        UserFullDto newCoach = userService.addRoleCoach(id);
        return ResponseEntity
                .ok()
                .body(newCoach);
    }

    @Operation(
            description = "Get endpoint for manager to remove coach role from user",
            summary = "Selected user with get a role coach",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"),
                    @ApiResponse(
                            description = "User with this id is not a coach",
                            responseCode = "400"
                    )
            })
    @GetMapping(value = "/remove_role_coach/{id}", produces = {"application/json"})
    public ResponseEntity<UserFullDto> removeRoleCoach(@PathVariable(value = "id") final Long id) {
        UserFullDto notAcoachAnymore = userService.removeRoleCoach(id);
        return ResponseEntity
                .ok()
                .body(notAcoachAnymore);
    }


}
