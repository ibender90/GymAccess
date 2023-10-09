package ru.geekbrains.gym.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.WorkoutDto;
import ru.geekbrains.gym.service.WorkoutService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/workout")
@PreAuthorize("hasRole('COACH')")
@RequiredArgsConstructor
public class WorkoutController {
    private final WorkoutService workoutService;

    @Operation(
            description = "POST endpoint for coach to create workout",
            summary = "Full information regarding new workout is required, " +
                    "list of workouts related with this coach is returned",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"),
                    @ApiResponse(
                            description = "no",
                            responseCode = "todo")
            })
    @PostMapping(value = "/new", produces = {"application/json"})
    public ResponseEntity<List<WorkoutDto>> addWorkout(@RequestBody @ParameterObject WorkoutDto workoutDto,
            Authentication authentication) {

        List<WorkoutDto> workoutDtoList = workoutService.createWorkout(workoutDto, authentication.getName());
        return ResponseEntity
                .ok()
                .body(workoutDtoList);
    }
}
