package ru.geekbrains.gym.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequest {

  @NotBlank(message = "first name cant be blank")
  private String firstname;

  @NotBlank(message = "last name cant be blank")
  private String lastname;

  @Email(message = "incorrect email")
  private String email;

  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{8,20}$",
          message = "Password must be 8-20 characters and include at least one digit and one lowercase letter " +
                  "(Password123 is ok and 123password is ok)")
  private String password;

}
