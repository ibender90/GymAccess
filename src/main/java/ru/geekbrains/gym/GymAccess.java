package ru.geekbrains.gym;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.model.PaidPeriod;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;
import ru.geekbrains.gym.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class GymAccess {

	public static void main(String[] args) {
		SpringApplication.run(GymAccess.class, args);
	}

	@Bean
	@Transactional
	public CommandLineRunner commandLineRunner(
			UserRepository userRepository,
			UserRoleRepository roleRepository,
			BCryptPasswordEncoder encoder
	) {
		return args -> {
			//todo insert admin with liquibase
			if(userRepository.existsById(1L)){
				return;
			}
			Optional<Role> roleAdmin = roleRepository.findByRoleName(RoleName.ADMIN);
			Optional<Role> roleUser = roleRepository.findByRoleName(RoleName.USER);

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

			PaidPeriod paidPeriod = PaidPeriod.builder()
					.dateFrom(simpleDateFormat.parse("20-01-1990 01:02:03"))
					.dateTo(simpleDateFormat.parse("1-01-2001 01:02:03"))
					.build();

			User admin = new User().toBuilder()
					.firstName("admin")
					.lastName("admin")
					.email("admin@admin.com")
					.password(encoder.encode("admin1234"))
					.roles(new HashSet<>(Set.of(roleAdmin.get(), roleUser.get())))
					.paidPeriod(paidPeriod)
					.build();

			userRepository.save(admin);
		};
	}
}
