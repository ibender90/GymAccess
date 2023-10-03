package ru.geekbrains.gym.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.gym.config.JwtAuthenticationFilter;
import ru.geekbrains.gym.config.SecurityConfiguration;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.enums.PermissionType;
import ru.geekbrains.gym.mocks.PaidPeriodDtoMock;
import ru.geekbrains.gym.mocks.UserWithPaidPeriodDtoMock;
import ru.geekbrains.gym.model.Role;
import ru.geekbrains.gym.repository.TokenRepository;
import ru.geekbrains.gym.service.JwtService;
import ru.geekbrains.gym.service.UserService;

import java.util.ArrayList;
import java.util.Set;

import static io.jsonwebtoken.Jwts.builder;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ManagerControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    private PaginatedResponseDto<UserWithPaidPeriodDto> correctResponse = new PaginatedResponseDto<>();

    {
        correctResponse.setData(new ArrayList<>(Set.of(
                UserWithPaidPeriodDtoMock.getMock(1L, PaidPeriodDtoMock.getPaidPeriodDtoMock())
        )));
    }


    @Test
    @WithMockUser(roles = "MANAGER" , setupBefore = TestExecutionEvent.TEST_METHOD)
    @DisplayName("Success reaching get endpoint for authorized user")
    public void testGetRequestForPageWithUserWithPaidPeriodDto() throws Exception {
        Mockito.when(userService.searchForUserAndPaidPeriod(Mockito.any(UserSearchDto.class))).thenReturn(correctResponse);
        this.mvc.perform(get("/api/v1/manager/search").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Success returning unauthorized response")
    public void givenAuthRequestOnPrivateService_shouldReturn403() throws Exception {
        this.mvc.perform(get("/api/v1/manager/search").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(setupBefore = TestExecutionEvent.TEST_METHOD)
    @DisplayName("Success returning unauthorized response for role USER")
    public void testAuthorisedUserCantGetAccessToThisEndpoint() throws Exception {
        Mockito.when(userService.searchForUserAndPaidPeriod(Mockito.any(UserSearchDto.class))).thenReturn(correctResponse);
        this.mvc.perform(get("/api/v1/manager/search")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
