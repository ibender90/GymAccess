package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.dto.UserDto;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.mapper.UserMapper;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new AppException("User with id:" + id + " not found"));
        return userMapper.toDto(user);
    }
    @Transactional
    public UserDto partialUpdate(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new AppException("User with id:" + userDto.getId()+ " not found"));
        userMapper.partialUpdate(user, userDto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    public void deleteById(Long id){
        //todo logging
        userRepository.deleteById(id);
    }

}
