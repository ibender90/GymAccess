package ru.geekbrains.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @Operation(
            description = "Get endpoint for admin with path variable",
            summary = "Admin can provide user id to change his role to manager",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    @GetMapping(value = "/set_manager/{id}", produces = {"application/json"})
    public ResponseEntity<UserFullDto> setRoleManager(@PathVariable(value = "id") final Long id) {

        UserFullDto managerAssigned = userService.addRoleManager(id);
        return ResponseEntity
                .ok()
                .body(managerAssigned);
    }

    @Operation(
            description = "Get endpoint for admin with parameter object",
            summary = "Admin searches for users by name/surname/email",
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
    public ResponseEntity<PaginatedResponseDto<UserFullDto>> getUserFullDtos(
            @ParameterObject UserSearchDto searchDto) {
        PaginatedResponseDto<UserFullDto> paginatedResponse = userService.searchFullUserInfo(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }

    @Operation(
            description = "Get endpoint for admin with path variable",
            summary = "Admin can get full information of the user selected by id",
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
    public ResponseEntity<UserFullDto> getUserById(@PathVariable(value = "id") final Long id) {
        //log.debug("REST request to get User : {}", id);
        UserFullDto userFound = userService.findUserFullDto(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }

    @Operation(
            description = "Put endpoint for admin with path parameter object",
            summary = "Admin can change first name, last name, email of selected user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403")
            })
    @PutMapping(value = "/update", produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UserFullDto> updateUser(@ParameterObject @RequestBody UserFullDto userToUpdate){
        UserFullDto updatedUser = userService.partialUpdate(userToUpdate);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }
}
