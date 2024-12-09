package cl.llancapan.illancapan.controller;

import cl.llancapan.illancapan.model.dto.UsersDTO;

import cl.llancapan.illancapan.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private UsersService usersService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private UsersDTO user1;
    private UsersDTO user2;
    private List<UsersDTO> usersList;

    @BeforeEach
    void setUp() {
        // Arrange
        user1 = new UsersDTO(
                1L,
                "github-id-1",
                "username1",
                "email1@email.com",
                "avatar-url-1",
                null,
                null
        );

        user2 = new UsersDTO(
                2L,
                "github-id-2",
                "username2",
                "email2@email.com",
                "avatar-url-2",
                null,
                null
        );

        usersList = Arrays.asList(user1,user2);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    void shouldCreateUser() throws Exception {

        // Simulamos la respuesta del servicio
        when(usersService.createUser(any(UsersDTO.class))).thenReturn(user1);

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        // Act & Assert
        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.githubId").value("github-id"))
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.avatarUrl").value("avatar-url"));


// Usamos assertEquals para verificar si los valores esperados coinciden
        assertEquals("github-id", user1.getGithubId(), "El githubId debería ser 'github-id'");
        assertEquals("github", user1.getUsername(), "El username debería ser 'username'");
        assertEquals("email@email.com", user1.getEmail(), "El email debería ser 'email@email.com'");
        assertEquals("avatar-url", user1.getAvatarUrl(), "El avatarUrl debería ser 'avatar-url'");


    }

    @Test
    void shouldGetAllUsers() throws Exception {

        when(usersService.getUserAll()).thenReturn(usersList);

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();

        mockMvc.perform(get("/user/all")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].githubId").value("github-id-1"))
                .andExpect(jsonPath("$[0].username").value("username1"))
                .andExpect(jsonPath("$[0].email").value("email1@email.com"))
                .andExpect(jsonPath("$[0].avatarUrl").value("avatar-url-1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].githubId").value("github-id-2"))
                .andExpect(jsonPath("$[1].username").value("username2"))
                .andExpect(jsonPath("$[1].email").value("email2@email.com"))
                .andExpect(jsonPath("$[1].avatarUrl").value("avatar-url-2"));
    }

//    @Test
//    void shouldGetUserById() throws Exception {
//
//        when(usersService.getUserById(any(usersList))).thenReturn(usersList);
//
//        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
//
//        mockMvc.perform(get("/user/{id}", userId)
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.id").value(userId))
//                .andExpect(jsonPath("$.githubId").value("github-id"))
//                .andExpect(jsonPath("$.username").value("username"))
//                .andExpect(jsonPath("$.email").value("email@email.com"))
//                .andExpect(jsonPath("$.avatarUrl").value("avatar-url"));
//
//    }

}
