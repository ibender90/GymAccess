package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.dto.*;
import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.exceptions.IncorrectPaidPeriodException;
import ru.geekbrains.gym.mapper.PaidPeriodMapper;
import ru.geekbrains.gym.mapper.UserMapper;
import ru.geekbrains.gym.model.PaidPeriod;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;
import ru.geekbrains.gym.repository.UserRoleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Data
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PaidPeriodMapper paidPeriodMapper;

    private final UserRoleRepository roleRepository;

    private final JwtService jwtService;

    public UserFullDto findUserFullDto(Long id){
         return userMapper.toDto(findByID(id));
    }

    public UserWithPaidPeriodDto findUserWithPaidPeriod(Long id){
        return userMapper.toDtoWithPaidPeriod(findByID(id));
    }
    @Transactional
    public UserFullDto partialUpdate(UserFullDto userFullDto) {
        User user = findByID(userFullDto.getId());
        userMapper.updateUserEntity(userFullDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserWithPaidPeriodDto editPaidPeriod(UserWithPaidPeriodDto userWithPaidPeriodDto) {

        if(userWithPaidPeriodDto.getPaidPeriod() != null && correctPaidPeriod(userWithPaidPeriodDto.getPaidPeriod())){
            User user = findByID(userWithPaidPeriodDto.getId());
            PaidPeriod paidPeriod = paidPeriodMapper.toEntity(userWithPaidPeriodDto.getPaidPeriod());
            user.setPaidPeriod(paidPeriod);
            return userMapper.toDtoWithPaidPeriod(userRepository.save(user));
        }
        throw new AppException(new IncorrectPaidPeriodException());
    }

    private boolean correctPaidPeriod(PaidPeriodDto paidPeriodDto)  {
        if(paidPeriodDto != null){
            SimpleDateFormat format = new SimpleDateFormat(Constant.DATE_FORMAT);
            try {
                Date dateFrom = format.parse(paidPeriodDto.getDateFrom());
                Date dateTo = format.parse(paidPeriodDto.getDateTo());
                Date dateNow = new Date();
                return dateFrom.after(dateNow) && dateTo.after(dateFrom);
            } catch (ParseException e) {
                throw new AppException(e.getMessage()) ;
            }
        }
        return false;
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
        logger.info("User with id " + id + " was deleted from database");
    }

    public PaginatedResponseDto<UserWithPaidPeriodDto> searchForUserAndPaidPeriod(UserSearchDto searchDto) {

        Page<User> usersPage = userRepository.findAll(searchDto.getSpecification(),searchDto.getPageable());
        List<UserWithPaidPeriodDto> userWithPaidPeriodDtos = userMapper.toDtosWithPaidPeriod(usersPage.getContent());

        return PaginatedResponseDto.<UserWithPaidPeriodDto>builder()
                .page(searchDto.getPage())
                .size(userWithPaidPeriodDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(userWithPaidPeriodDtos)
                .build();
    }
    public PaginatedResponseDto<UserFullDto> searchFullUserInfo(UserSearchDto searchDto) {

        Page<User> usersPage = userRepository.findAll(searchDto.getSpecification(),searchDto.getPageable());
        List<UserFullDto> users = userMapper.toDtos(usersPage.getContent());

        return PaginatedResponseDto.<UserFullDto>builder()
                .page(searchDto.getPage())
                .size(users.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(users)
                .build();
    }

    @Transactional
    public UserFullDto addRoleManager(Long userId) {
        Role manager = roleRepository.findByRoleName(RoleName.MANAGER)
                .orElseThrow(() -> new AppException("CRITICAL ERROR, role MANAGER not found in the database"));
        User user = findByID(userId);
        Set<Role> roles = user.getRoles();
        roles.add(manager);
        return userMapper.toDto(userRepository.save(user));
    }

    public User findByID(Long id){
        return userRepository.findById(id).orElseThrow(() -> new AppException("User with id:" + id + " not found"));
    }

    public UserFullDto findUserByEmail(String email){
        return userMapper.toDto(userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("User with email: " + email + " not found")));
    }

    @Transactional
    public UserFullDto addRoleCoach(Long userId){
        Role coach = roleRepository.findByRoleName(RoleName.COACH)
                .orElseThrow(() -> new AppException("CRITICAL ERROR, role COACH not found in the database"));
        User user = findByID(userId);
        Set<Role> roles = user.getRoles();
        roles.add(coach);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserFullDto removeRoleCoach(Long userId){
        User user = findByID(userId);
        Set<Role> roles = user.getRoles();

        if(roles.stream().noneMatch(role ->
                role.getRoleName().name().equals(RoleName.COACH.name()))){
            throw new AppException("USER WITH ID "+ userId + " is not a coach", 400);
        }
        Role coach = roles.stream().filter(role ->
                role.getRoleName().name().equals(RoleName.COACH.name())).findFirst().get();
        roles.remove(coach);
        return userMapper.toDto(userRepository.save(user));
    }
}
