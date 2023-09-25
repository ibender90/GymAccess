package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.mapper.UserMapper;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;
import ru.geekbrains.gym.repository.UserRoleRepository;

import java.util.List;
import java.util.Set;

@Service
@Data
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final UserRoleRepository roleRepository;

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

    public PaginatedResponseDto search(UserSearchDto searchDto) {

        Page<User> usersPage = userRepository.findAll(searchDto.getSpecification(),searchDto.getPageable());
        List<UserDto> userDtos = userMapper.toDtos(usersPage.getContent());

        return PaginatedResponseDto.builder()
                .page(searchDto.getPage())
                .size(userDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(userDtos)
                .build();
    }

    @Transactional
    public UserDto setRoleManager(Long userId) {
        Role manager = roleRepository.findByRoleName(RoleName.MANAGER).get();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with id:" + userId + " not found"));

        Set<Role> roles = user.getRoles();
        roles.add(manager);
        return userMapper.toDto(userRepository.save(user));
    }
}
