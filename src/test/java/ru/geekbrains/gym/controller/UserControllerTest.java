package ru.geekbrains.gym.controller;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.geekbrains.gym.dto.PaginatedResponseDto;
import ru.geekbrains.gym.dto.UserSearchDto;
import ru.geekbrains.gym.dto.UserWithPaidPeriodDto;
import ru.geekbrains.gym.mocks.PaidPeriodDtoMock;
import ru.geekbrains.gym.mocks.UserWithPaidPeriodDtoMock;
import ru.geekbrains.gym.repository.TokenRepository;
import ru.geekbrains.gym.service.JwtService;
import ru.geekbrains.gym.service.UserService;

import java.util.ArrayList;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;

    private UserWithPaidPeriodDto correctResponse =  UserWithPaidPeriodDtoMock.getMock(1L, PaidPeriodDtoMock.getPaidPeriodDtoMock());


    @WithMockUser
    @Test
    @DisplayName("Success reaching get endpoint for authorized user")
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        Mockito.when(userService.findUserByEmail(Mockito.anyString())).thenReturn(correctResponse);
        this.mvc.perform(get("/api/v1/user/").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Success returning unauthorized response")
    public void givenAuthRequestOnPrivateService_shouldReturn401() throws Exception {
        this.mvc.perform(get("/api/v1/user/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
