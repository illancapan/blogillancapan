package cl.llancapan.illancapan.controller;

import cl.llancapan.illancapan.model.dto.UserDTO;

import cl.llancapan.illancapan.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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

@WebMvcTest(UserController.class)
class UserControllerTest {

    @InjectMocks
    private UserController usersController;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private UserDTO user1;
    private UserDTO user2;
    private List<UserDTO> usersList;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();

        objectMapper = new ObjectMapper();

        user1 = new UserDTO(
                1L,
                "github-id-1",
                "username1",
                "email1@email.com",
                "avatar-url-1",
                null,
                null
        );

        user2 = new UserDTO(
                2L,
                "github-id-2",
                "username2",
                "email2@email.com",
                "avatar-url-2",
                null,
                null
        );

        usersList = Arrays.asList(user1, user2);
    }

    @Test
    void shouldCreateUser() throws Exception {

        when(userService.createUser(any(UserDTO.class))).thenReturn(user1);

        mockMvc.perform(post("/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.githubId").value("github-id-1"))
                .andExpect(jsonPath("$.username").value("username1"))
                .andExpect(jsonPath("$.email").value("email1@email.com"))
                .andExpect(jsonPath("$.avatarUrl").value("avatar-url-1"));

        assertEquals("github-id-1", user1.getGithubId(), "El githubId debería ser 'github-id-1'");
        assertEquals("username1", user1.getUsername(), "El username debería ser 'username1'");
        assertEquals("email1@email.com", user1.getEmail(), "El email debería ser 'email1@email.com'");
        assertEquals("avatar-url-1", user1.getAvatarUrl(), "El avatarUrl debería ser 'avatar-url-1'");


    }

    @Test
    void shouldGetAllUsers() throws Exception {

        when(userService.getUserAll()).thenReturn(usersList);

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

    @Test
    void shouldGetByIdUser() throws Exception {

        when(userService.getUserById(1L)).thenReturn(user1);

        mockMvc.perform(get("/user/1")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.githubId").value("github-id-1"))
                .andExpect(jsonPath("$.username").value("username1"))
                .andExpect(jsonPath("$.email").value("email1@email.com"))
                .andExpect(jsonPath("$.avatarUrl").value("avatar-url-1"));

    }
}
