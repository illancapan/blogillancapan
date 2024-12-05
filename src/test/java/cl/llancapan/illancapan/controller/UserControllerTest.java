package cl.llancapan.illancapan.controller;

import cl.llancapan.illancapan.model.dto.UserDTO;
import cl.llancapan.illancapan.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

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

        UserDTO result = userController.createUser(userDTO);

        // Act & Assert
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.githubId").value("github-id"))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.avatarUrl").value("avatar-url"));

        // Opcional: Verificaciones adicionales
        // Aquí podrías agregar verificaciones específicas según tu lógica de negocio
    }

    @Test
    void shouldHandleInvalidUserCreation() throws Exception {
        // Arrange
        UserDTO invalidUserDTO = new UserDTO(
                null,
                "",
                "",
                "invalid-email",
                null,
                null,
                null
        );

        // Act & Assert
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDTO)))
                .andExpect(status().isBadRequest());
    }
}