package ru.geekbrains.gym.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserSearchDto;
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
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PaidPeriodMapper paidPeriodMapper;

    private final UserRoleRepository roleRepository;

    public UserFullDto findById(Long id){
         return userMapper.toDto(findByID(id));
    }
    @Transactional
    public UserFullDto partialUpdate(UserFullDto userFullDto) { //todo fix
        User user = findByID(userFullDto.getId());
        userMapper.partialUpdate(user, userFullDto);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserFullDto editPaidPeriod(UserFullDto userFullDto) {

        if(userFullDto.getPaidPeriod() != null && correctPaidPeriod(userFullDto.getPaidPeriod())){
            User user = findByID(userFullDto.getId());
            PaidPeriod paidPeriod = paidPeriodMapper.toEntity(userFullDto.getPaidPeriod());
            user.setPaidPeriod(paidPeriod);
            return userMapper.toDto(userRepository.save(user));
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
        //todo logging
        userRepository.deleteById(id);
    }

    public PaginatedResponseDto search(UserSearchDto searchDto) {

        Page<User> usersPage = userRepository.findAll(searchDto.getSpecification(),searchDto.getPageable());
        List<UserFullDto> userFullDtos = userMapper.toDtos(usersPage.getContent());

        return PaginatedResponseDto.builder()
                .page(searchDto.getPage())
                .size(userFullDtos.size())
                .sortingFields(searchDto.getSort())
                .sortDirection(searchDto.getDir().name())
                .data(userFullDtos)
                .build();
    }

    @Transactional
    public UserFullDto setRoleManager(Long userId) {
        Role manager = roleRepository.findByRoleName(RoleName.MANAGER).get();
        User user = findByID(userId);
        Set<Role> roles = user.getRoles();
        roles.add(manager);
        return userMapper.toDto(userRepository.save(user));
    }

    private User findByID(Long id){
        return userRepository.findById(id).orElseThrow(() -> new AppException("User with id:" + id + " not found"));
    }
}
