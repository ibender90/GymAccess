package ru.geekbrains.gym.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.dto.RoleDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.enums.RoleName;
import ru.geekbrains.gym.exceptions.AppException;
import ru.geekbrains.gym.mapper.*;
import ru.geekbrains.gym.mocks.*;
import ru.geekbrains.gym.model.PaidPeriod;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.Token;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;
import ru.geekbrains.gym.repository.UserRoleRepository;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    UserRoleRepository roleRepository;

    @InjectMocks
    private UserService userService;
    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Spy
    private PaidPeriodMapper paidPeriodMapper = new PaidPeriodMapperImpl();;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    public UserServiceTest() throws ParseException {
    }

    @BeforeEach
    public void initMapperDependencies() {
        RoleMapper roleMapper = new RoleMapperImpl();

        ReflectionTestUtils.setField(userMapper, "roleMapper", roleMapper);
        ReflectionTestUtils.setField(userMapper, "paidPeriodMapper", paidPeriodMapper);
    }

    private Role role = RoleUserMock.getUserRoleMock();
    private Token token = TokenMock.getTokenMock();
    private PaidPeriod paidPeriod = PaidPeriodMock.getMockPaidPeriod(2L);
    private User user = UserMock.getUserMock(1L, paidPeriod, role, token);

    @Test
    @DisplayName("Success finding user including paid period")
    public void testFindUserWithPaidPeriod() {

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.user));

        UserWithPaidPeriodDto userWithPaidPeriodDto = userService.findUserWithPaidPeriod(user.getId());

        Assertions.assertNotNull(userWithPaidPeriodDto.getPaidPeriod());
        //other fields were tested in UserMapperTest
    }

    @Test
    @DisplayName("Success partial user update")
    public void testUserPartialUpdate() {
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(this.user));

        UserFullDto userDtoWithChanges = UserFullDto.builder()
                .id(this.user.getId())
                .firstName("New firstname")
                .lastName("New lastname")
                .email("new email")
                .roles(new HashSet<RoleDto>(Set.of(RoleDtoMock.getRoleDtoUSER()))) //this field to be ignored
                .paidPeriod(PaidPeriodDtoMock.getPaidPeriodDtoMock()) //this field to be ignored
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(this.user); //not important

        userService.partialUpdate(userDtoWithChanges);

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        User savedUser = userArgumentCaptor.getValue();
        Assertions.assertEquals(savedUser.getFirstName(), userDtoWithChanges.getFirstName());
        Assertions.assertEquals(savedUser.getLastName(), userDtoWithChanges.getLastName());
        Assertions.assertEquals(savedUser.getEmail(), userDtoWithChanges.getEmail());
        Assertions.assertEquals(savedUser.getPaidPeriod().getDateFrom(), this.user.getPaidPeriod().getDateFrom());
        Assertions.assertEquals(savedUser.getPaidPeriod().getDateTo(), this.user.getPaidPeriod().getDateTo());
        Assertions.assertEquals(savedUser.getPaidPeriod().getId(), this.user.getPaidPeriod().getId());
        Assertions.assertEquals(savedUser.getRoles(), this.user.getRoles());
    }
    @Test
    @DisplayName("Success updating correct paid period for user")
    public void updatePaidPeriodTest(){
        //Paid period mock has a correct date
        User testUser = UserMock.getUserMock(1L, paidPeriod, role, token);

        UserWithPaidPeriodDto userToUpdate = userMapper.toDtoWithPaidPeriod(testUser);

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(testUser);

        userService.editPaidPeriod(userToUpdate);

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        Assertions.assertEquals(paidPeriod.getDateFrom(), savedUser.getPaidPeriod().getDateFrom());
        Assertions.assertEquals(paidPeriod.getDateTo(), savedUser.getPaidPeriod().getDateTo());
    }
    @Test
    @DisplayName("Success returning an error if paid period is incorrect (dateFrom is after dateTo) and dateFrom is before current date")
    public void PaidPeriodTestThrowsException(){
        UserWithPaidPeriodDto userWithIncorrectPaidPeriod = new UserWithPaidPeriodDto();
        PaidPeriodDto incorrectPaidPeriod = new PaidPeriodDto(1L, "12-12-2024 00:00:00", "12-12-2023 00:00:00");

        userWithIncorrectPaidPeriod.setId(1L);
        userWithIncorrectPaidPeriod.setPaidPeriod(incorrectPaidPeriod);

        Assertions.assertThrows(AppException.class, () -> userService.editPaidPeriod(userWithIncorrectPaidPeriod));

        incorrectPaidPeriod.setDateFrom("12-12-1995 00:00:00");
        Assertions.assertThrows(AppException.class, () -> userService.editPaidPeriod(userWithIncorrectPaidPeriod));
    }

    @Test
    @DisplayName("Success deleting user by id")
    public void deleteByIdTest(){
        Long idToCheck = 2L;
        userService.deleteById(idToCheck);

        Mockito.verify(userRepository, times(1)).deleteById(idToCheck);
    }

    @Test
    @DisplayName("Success adding role manager to user")
    public void addRoleManagerTest(){

        Long id = 3L;
        User notManager = UserMock.getUserMock(id, paidPeriod, role, token);

        Role mockManager = Role.builder().roleName(RoleName.MANAGER).build();
        Mockito.when(roleRepository.findByRoleName(Mockito.any(RoleName.class))).thenReturn(Optional.ofNullable(mockManager));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(notManager));

        userService.addRoleManager(id);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        Set<Role> roleSet = userArgumentCaptor.getValue().getRoles();
        Assertions.assertTrue(roleSet.contains(mockManager));

        assert mockManager != null;
        Mockito.verify(roleRepository, times(1)).findByRoleName(mockManager.getRoleName());
    }

    @Test
    @DisplayName("Success adding role coach to user")
    public void addRoleCoachTest(){

        Long id = 4L;
        User userNotCoach = UserMock.getUserMock(id, paidPeriod, role, token);

        Role mockCoachRole = Role.builder().roleName(RoleName.COACH).build();
        Mockito.when(roleRepository.findByRoleName(Mockito.any(RoleName.class))).thenReturn(Optional.ofNullable(mockCoachRole));
        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(userNotCoach));

        userService.addRoleCoach(id);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());

        Set<Role> roleSet = userArgumentCaptor.getValue().getRoles();
        Assertions.assertTrue(roleSet.contains(mockCoachRole));

        assert mockCoachRole != null;
        Mockito.verify(roleRepository, times(1)).findByRoleName(mockCoachRole.getRoleName());
    }

    @Test
    @DisplayName("Success removing role coach from user")
    public void removeRoleCoachTest(){
        Role roleUser = new Role(1L, RoleName.USER);
        Role roleCoach = new Role(2L, RoleName.COACH);
        Set<Role> initialRoleSet = new HashSet<>(Set.of(roleUser, roleCoach));

        User user = UserMock.getUserMock(6L, paidPeriod, role, token);
        user.setRoles(initialRoleSet);

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
        userService.removeRoleCoach(user.getId());

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        Set<Role> modifiedSet = userArgumentCaptor.getValue().getRoles();

        Assertions.assertFalse(modifiedSet.contains(roleCoach));
    }

}

