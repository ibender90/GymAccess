package ru.geekbrains.gym.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gym.dto.AuthenticationRequest;
import ru.geekbrains.gym.dto.AuthenticationResponse;
import ru.geekbrains.gym.dto.UserRegisterRequest;
import ru.geekbrains.gym.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @Valid @RequestBody UserRegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }
  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }


}
