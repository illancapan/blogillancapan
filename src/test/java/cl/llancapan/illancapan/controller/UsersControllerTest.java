package cl.llancapan.illancapan.controller;

import cl.llancapan.illancapan.model.dto.UsersDTO;

import cl.llancapan.illancapan.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void shouldCreateUser() throws Exception {

        // Arrange
        UsersDTO usersDTO = new UsersDTO(
                null,
                "github-id",
                "username",
                "email@email.com",
                "avatar-url",
                null,
                null
        );

        // Simulamos la respuesta del servicio
        when(usersService.createUser(any(UsersDTO.class))).thenReturn(usersDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        // Act & Assert
        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(usersDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.githubId").value("github-id"))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.avatarUrl").value("avatar-url"));


// Usamos assertEquals para verificar si los valores esperados coinciden
        assertEquals("github-id", usersDTO.getGithubId(), "El githubId debería ser 'github-id'");
        assertEquals("username", usersDTO.getUsername(), "El username debería ser 'username'");
        assertEquals("email@email.com", usersDTO.getEmail(), "El email debería ser 'email@email.com'");
        assertEquals("avatar-url", usersDTO.getAvatarUrl(), "El avatarUrl debería ser 'avatar-url'");


    }
}
