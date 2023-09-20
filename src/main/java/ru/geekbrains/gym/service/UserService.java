package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

}
