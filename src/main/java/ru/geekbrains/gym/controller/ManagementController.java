package ru.geekbrains.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.service.UserService;

@RestController
@RequestMapping("/api/v1/management")
@Tag(name = "Management")
@PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
@RequiredArgsConstructor
public class ManagementController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('manager:read')")
    @GetMapping(value = "/search", produces = {"application/json"})
    public ResponseEntity<PaginatedResponseDto<UserWithPaidPeriodDto>> getUsersWithPaidPeriod(UserSearchDto searchDto) {
        PaginatedResponseDto<UserWithPaidPeriodDto> paginatedResponse = userService.searchForUserAndPaidPeriod(searchDto);
        return ResponseEntity
                .ok()
                .body(paginatedResponse);
    }

    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    @PatchMapping(value = "/update_payment", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<UserWithPaidPeriodDto> updatePayment(@RequestBody UserWithPaidPeriodDto userWithPaidPeriodDto){
        UserWithPaidPeriodDto updatedUser = userService.editPaidPeriod(userWithPaidPeriodDto);
        return ResponseEntity
                .ok()
                .body(updatedUser);
    }

    @PreAuthorize("hasAuthority('manager:read')")
    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<UserWithPaidPeriodDto> getUserById(@PathVariable(value = "id") final Long id) {
        //log.debug("REST request to get User : {}", id);
        UserWithPaidPeriodDto userFound = userService.findUserWithPaidPeriod(id);
        return ResponseEntity
                .ok()
                .body(userFound);
    }


    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @GetMapping
    public String get() {
        return "GET:: management controller";
    }
    @PostMapping
    public String post() {
        return "POST:: management controller";
    }
    @PutMapping
    public String put() {
        return "PUT:: management controller";
    }
    @DeleteMapping
    public String delete() {
        return "DELETE:: management controller";
    }
}
