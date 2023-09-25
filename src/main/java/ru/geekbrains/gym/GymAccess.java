package ru.geekbrains.gym;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;
import ru.geekbrains.gym.repository.UserRoleRepository;
import ru.geekbrains.gym.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class GymAccess {

	public static void main(String[] args) {
		SpringApplication.run(GymAccess.class, args);
	}

	//@Bean //uncomment to create an admin
	public CommandLineRunner commandLineRunner(
			AuthenticationService service, UserRepository userRepository,
			UserRoleRepository roleRepository,
			BCryptPasswordEncoder encoder
	) {
		return args -> {
			Optional<Role> roleAdmin = roleRepository.findByRoleName(RoleName.ADMIN);
			Optional<Role> roleUser = roleRepository.findByRoleName(RoleName.USER);

			User admin = new User().toBuilder()
					.firstName("admin")
					.lastName("admin")
					.email("admin@admin.com")
					.password(encoder.encode("admin1234"))
					.roles(new HashSet<>(Set.of(roleAdmin.get(), roleUser.get())))
					.build();

			userRepository.save(admin);
		};
	}
}
