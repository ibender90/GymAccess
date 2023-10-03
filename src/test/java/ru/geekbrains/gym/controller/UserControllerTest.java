package ru.geekbrains.gym.controller;





import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.test.context.support.WithMockUser;


import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.geekbrains.gym.repository.TokenRepository;
import ru.geekbrains.gym.service.JwtService;
import ru.geekbrains.gym.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    JwtService jwtService;

    @MockBean
    TokenRepository tokenRepository;

    @MockBean
    UserService userService;

    @WithMockUser(value = "authorisedUser")
    @Test
    @DisplayName("Success reaching get endpoint for authorized user")
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        this.mvc.perform(get("/api/v1/user/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Success returning unauthorized response")
    public void givenAuthRequestOnPrivateService_shouldReturn401() throws Exception {
        this.mvc.perform(get("/api/v1/user/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
