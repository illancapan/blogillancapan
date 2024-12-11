package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UserDTO;
import cl.llancapan.illancapan.model.entity.Users;
import cl.llancapan.illancapan.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private Users user1;
    private Users user2;
    private UserDTO user1DTO;
    private UserDTO user2DTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new Users(
                1L,
                "github-id-1",
                "username1",
                "email1@email.com",
                "avatar-url-1",
                LocalDateTime.now(),
                LocalDateTime.now());

        user2 = new Users(
                2L,
                "github-id-2",
                "username2",
                "email2@email.com",
                "avatar-url-2",
                LocalDateTime.now(),
                LocalDateTime.now());

        user1DTO = new UserDTO(
                1L,
                "github-id-1",
                "username1",
                "email1@email.com",
                "avatar-url-1",
                LocalDateTime.now(),
                LocalDateTime.now());

        user2DTO = new UserDTO(
                2L,
                "github-id-2",
                "username2",
                "email2@email.com",
                "avatar-url-2",
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    @Test
    void testGetAllUser_shouldGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(modelMapper.map(any(Users.class), eq(UserDTO.class)))
                .thenAnswer(invocation -> {
                    Users user = invocation.getArgument(0);
                    return new UserDTO(
                            user.getId(),
                            user.getGithubId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getAvatarUrl(),
                            user.getCreatedAt(),
                            user.getUpdatedAt());
                });

        List<UserDTO> result = userService.getUserAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(Users.class), eq(UserDTO.class));
    }

    @Test
    void testGetUser_ShouldReturnUserDTO_WhenUserExists() {
        Users testUser = new Users(
                1L,
                "test-github-id",
                "test-username",
                "test-email@email.com",
                "test-avatar",
                LocalDateTime.now(),
                LocalDateTime.now());
        UserDTO expectedDTO = new UserDTO(
                1L,
                "test-github-id",
                "test-username",
                "test-email@email.com",
                "test-avatar",
                LocalDateTime.now(),
                LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(modelMapper.map(testUser, UserDTO.class)).thenReturn(expectedDTO);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(expectedDTO.getId(), result.getId());
        assertEquals(expectedDTO.getUsername(), result.getUsername());
        assertEquals(expectedDTO.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(testUser, UserDTO.class);
    }


    @Test
    void testCreateUser_ShouldReturnUserDTO_WhenUserIsCreated() {
        when(modelMapper.map(user1DTO, Users.class)).thenReturn(user1);
        when(userRepository.save(user1)).thenReturn(user1);
        when(modelMapper.map(user1, UserDTO.class)).thenReturn(user1DTO);

        UserDTO result = userService.createUser(user1DTO);

        assertNotNull(result, "El resultado no debe ser nulo.");
        assertEquals(user1DTO.getUsername(), result.getUsername());
        assertEquals(user1DTO.getEmail(), result.getEmail());

        verify(userRepository, times(1)).save(user1);
        verify(modelMapper, times(1)).map(user1DTO, Users.class);
        verify(modelMapper, times(1)).map(user1, UserDTO.class);
    }


    @Test
    void testGetUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> userService.getUserById(1L),
                "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no existe");

        assertEquals("User Not Found", thrown.getMessage(), "El mensaje de la excepción no es el esperado.");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> userService.deleteUser(1L),
                "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no es encontrado");

        assertEquals("User Not Found", thrown.getMessage(), "El mensaje de la excepción no es el esperado.");
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser_ShouldDeleteUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user1);
    }
}
