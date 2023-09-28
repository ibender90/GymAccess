package ru.geekbrains.gym.mapper;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import ru.geekbrains.gym.constant.Constant;
import ru.geekbrains.gym.dto.PaidPeriodDto;
import ru.geekbrains.gym.dto.RoleDto;
import ru.geekbrains.gym.dto.UserFullDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.mocks.PaidPeriodMock;
import ru.geekbrains.gym.mocks.RoleUserMock;
import ru.geekbrains.gym.mocks.TokenMock;
import ru.geekbrains.gym.mocks.UserMock;
import ru.geekbrains.gym.model.PaidPeriod;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.model.Token;
import ru.geekbrains.gym.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Spy
    UserMapper userMapper = new UserMapperImpl();

    @BeforeEach
    public void before(){
        initMapperDependencies();
        initUser();
    }

    public void initMapperDependencies(){
        RoleMapper roleMapper = new RoleMapperImpl();
        PaidPeriodMapper paidPeriodMapper = new PaidPeriodMapperImpl();

        ReflectionTestUtils.setField(userMapper,"roleMapper",roleMapper);
        ReflectionTestUtils.setField(userMapper,"paidPeriodMapper",paidPeriodMapper);
    }

    public void initUser(){
        this.dto = userMapper.toDto(this.user);
    }

    private Role role = RoleUserMock.getUserRoleMock();
    private Token token = TokenMock.getTokenMock();
    private PaidPeriod paidPeriod = PaidPeriodMock.getMockPaidPeriod(2L);
    private User user = UserMock.getUserMock(1L, paidPeriod, role, token);

    private UserFullDto dto = null;

    public UserMapperTest() throws ParseException {
    }



    @Test
    @DisplayName("User mapped to userFullDto correctly (Fields except for nested objects)")
    public void testUserToDtoMappingWithoutNestedObjects(){
        Assertions.assertEquals(user.getId(), dto.getId());
        Assertions.assertEquals(user.getEmail(), dto.getEmail());
        Assertions.assertEquals(user.getFirstName(), dto.getFirstName());
        Assertions.assertEquals(user.getLastName(), dto.getLastName());
    }
    @Test
    @DisplayName("UserRole from User entity is mapped correctly to Dto")
    public void testUserRoleMapping(){
        Set<RoleDto> roleDto = dto.getRoles();
        Assertions.assertTrue(roleDto.stream().anyMatch(item -> item.getRoleName().equals("USER")));
    }

    @Test
    @DisplayName("Users PaidPeriod entity is mapped correctly to Dto")
    public void testUserPaidPeriodMapping(){
        PaidPeriodDto paidPeriodDto = dto.getPaidPeriod();
        PaidPeriod paidPeriod = user.getPaidPeriod();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

        Assertions.assertEquals(paidPeriodDto.getDateFrom(), simpleDateFormat.format(paidPeriod.getDateFrom()));
        Assertions.assertEquals(paidPeriodDto.getDateTo(), simpleDateFormat.format(paidPeriod.getDateTo()));
    }

    @Test
    @DisplayName("User entity  is mapped correctly to UserWithPaidPeriodDto")
    public void testUserToDtoWithPaidPeriodMapping(){
        UserWithPaidPeriodDto userWithPaidPeriodDto = userMapper.toDtoWithPaidPeriod(user);
        PaidPeriodDto paidPeriodDto = userWithPaidPeriodDto.getPaidPeriodDto();
        PaidPeriod paidPeriod = user.getPaidPeriod();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constant.DATE_FORMAT);

        Assertions.assertNotNull(userWithPaidPeriodDto.getPaidPeriodDto());
        Assertions.assertEquals(paidPeriodDto.getDateFrom(), simpleDateFormat.format(paidPeriod.getDateFrom()));
        Assertions.assertEquals(paidPeriodDto.getDateTo(), simpleDateFormat.format(paidPeriod.getDateTo()));

    }
}
