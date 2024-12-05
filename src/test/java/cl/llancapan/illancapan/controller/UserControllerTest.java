package cl.llancapan.illancapan.controller;

import cl.llancapan.illancapan.model.dto.UserDTO;

import cl.llancapan.illancapan.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private MockMvc mockMvc ;
    private ObjectMapper objectMapper = new ObjectMapper();



    @Test
    void shouldCreateUser() throws Exception {

        // Arrange
        UserDTO userDTO = new UserDTO(
                null,
                "github-id",
                "username",
                "email@email.com",
                "avatar-url",
                null,
                null
        );

        // Simulamos la respuesta del servicio
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        // Act & Assert
        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.githubId").value("github-id"))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.avatarUrl").value("avatar-url"));


// Usamos assertEquals para verificar si los valores esperados coinciden
        assertEquals("github-id", userDTO.getGithubId(), "El githubId debería ser 'github-id'");
        assertEquals("username", userDTO.getUsername(), "El username debería ser 'username'");
        assertEquals("email@email.com", userDTO.getEmail(), "El email debería ser 'email@email.com'");
        assertEquals("avatar-url", userDTO.getAvatarUrl(), "El avatarUrl debería ser 'avatar-url'");


    }
}
