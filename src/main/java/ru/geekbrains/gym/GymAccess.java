package ru.geekbrains.gym;

import ru.geekbrains.gym.auth.AuthenticationService;
import ru.geekbrains.gym.dto.UserRegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GymAccess {

	public static void main(String[] args) {
		SpringApplication.run(GymAccess.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = UserRegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@mail.com")
					.password("password")
					//.role(ADMIN)
					.build();

			System.out.println("Admin token: " + service.register(admin).getAccessToken());

			var manager = UserRegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("manager@mail.com")
					.password("password")
					//.role(MANAGER)
					.build();
			System.out.println("Manager token: " + service.register(manager).getAccessToken());

		};
	}
}
