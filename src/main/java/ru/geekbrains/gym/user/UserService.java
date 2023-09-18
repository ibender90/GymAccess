package ru.geekbrains.gym.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }

}
