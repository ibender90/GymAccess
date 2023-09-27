package ru.geekbrains.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.service.UserService;

//user controller gives access to manager or admin to view userDto, to set paid period for access to gym
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('manager:read')")
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<UserDto> getUserById(@PathVariable(value = "id") final Long id) {
        //log.debug("REST request to get User : {}", id);
        UserDto userFound = userService.findById(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }

    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('manager:read')")
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto> getUsers(UserSearchDto searchDto) {
        PaginatedResponseDto paginatedResponse = userService.search(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }

}
