package ru.geekbrains.gym.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserFullDto;
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
    public ResponseEntity<UserFullDto> getUserById(@PathVariable(value = "id") final Long id) {
        //log.debug("REST request to get User : {}", id);
        UserFullDto userFound = userService.findById(id);
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

//    @PatchMapping(value = "/employees", produces = {"application/json"}, consumes = {"application/json"})
//    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable(value = "id") @RequestBody EmployeeDto employeeDto) {
//        EmployeeDto employeeFound = employeeService.partialUpdate(employeeDto);
//        return ResponseEntity
//                .ok()
//                .body(employeeFound);
//    }

    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    @PatchMapping(value = "/update_payment", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<UserFullDto> updatePayment(@RequestBody UserFullDto userFullDto){
        UserFullDto updatedUser = userService.editPaidPeriod(userFullDto);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }
}
