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
import ru.geekbrains.gym.mapper.*;
import ru.geekbrains.gym.mocks.*;
import ru.geekbrains.gym.model.PaidPeriod;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.Token;
import ru.geekbrains.gym.model.User;
import ru.geekbrains.gym.repository.UserRepository;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

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
    @DisplayName("Paid period is updated correctly")
    public void updatePaidPeriodTest(){
        //Paid period mock has a correct date
        User testUser = UserMock.getUserMock(1L, paidPeriod, role, token);

        UserWithPaidPeriodDto userToUpdate = userMapper.toDtoWithPaidPeriod(testUser);

        Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(testUser);

        UserWithPaidPeriodDto u = userService.editPaidPeriod(userToUpdate);
        System.out.println(u.getPaidPeriod());

        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User savedUser = userArgumentCaptor.getValue();

        System.out.println(savedUser);
        Assertions.assertEquals(paidPeriod.getDateFrom(), savedUser.getPaidPeriod().getDateFrom());
        Assertions.assertEquals(paidPeriod.getDateTo(), savedUser.getPaidPeriod().getDateTo());
    }
}

