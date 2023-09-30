package ru.geekbrains.gym.controller;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.service.UserService;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:update')")
    @GetMapping(value = "/set_manager/{id}", produces = {"application/json"})
    public ResponseEntity<UserFullDto> setRoleManager(@PathVariable(value = "id") final Long id) {

        UserFullDto managerAssigned = userService.setRoleManager(id);
        return ResponseEntity
                .ok()
                .body(managerAssigned);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping(value = "/all", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<UserFullDto>> getUserFullDtos(UserSearchDto searchDto) {
        PaginatedResponseDto<UserFullDto> paginatedResponse = userService.searchFullUserInfo(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }

    @PreAuthorize("hasAuthority('admin:read')")
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<UserFullDto> getUserById(@PathVariable(value = "id") final Long id) {
        //log.debug("REST request to get User : {}", id);
        UserFullDto userFound = userService.findUserFullDto(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }

    @PutMapping(value = "/update", produces = {"application/json"})
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<UserFullDto> updateUser(@RequestBody UserFullDto userToUpdate){
        UserFullDto updatedUser = userService.partialUpdate(userToUpdate);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }
    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }
    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    @Hidden
    public String post() {
        return "POST:: admin controller";
    }
    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    @Hidden
    public String put() {
        return "PUT:: admin controller";
    }
    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    @Hidden
    public String delete() {
        return "DELETE:: admin controller";
    }
}
