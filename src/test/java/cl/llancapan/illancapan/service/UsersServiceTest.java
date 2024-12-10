package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.model.entity.Users;
import cl.llancapan.illancapan.repository.UsersRepository;
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
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UsersService usersService;

    private Users user1;
    private Users user2;
    private UsersDTO user1DTO;
    private UsersDTO user2DTO;

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

        user1DTO = new UsersDTO(
                1L,
                "github-id-1",
                "username1",
                "email1@email.com",
                "avatar-url-1",
                LocalDateTime.now(),
                LocalDateTime.now());

        user2DTO = new UsersDTO(
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
        when(usersRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        when(modelMapper.map(any(Users.class), eq(UsersDTO.class)))
                .thenAnswer(invocation -> {
                    Users user = invocation.getArgument(0);
                    return new UsersDTO(
                            user.getId(),
                            user.getGithubId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getAvatarUrl(),
                            user.getCreatedAt(),
                            user.getUpdatedAt());
                });

        List<UsersDTO> result = usersService.getUserAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(usersRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(Users.class), eq(UsersDTO.class));
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
        UsersDTO expectedDTO = new UsersDTO(
                1L,
                "test-github-id",
                "test-username",
                "test-email@email.com",
                "test-avatar",
                LocalDateTime.now(),
                LocalDateTime.now());

        when(usersRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(modelMapper.map(testUser, UsersDTO.class)).thenReturn(expectedDTO);

        UsersDTO result = usersService.getUserById(1L);

        assertNotNull(result);
        assertEquals(expectedDTO.getId(), result.getId());
        assertEquals(expectedDTO.getUsername(), result.getUsername());
        assertEquals(expectedDTO.getEmail(), result.getEmail());

        verify(usersRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(testUser, UsersDTO.class);
    }


    @Test
    void testCreateUser_ShouldReturnUserDTO_WhenUserIsCreated() {
        when(modelMapper.map(user1DTO, Users.class)).thenReturn(user1);
        when(usersRepository.save(user1)).thenReturn(user1);
        when(modelMapper.map(user1, UsersDTO.class)).thenReturn(user1DTO);

        UsersDTO result = usersService.createUser(user1DTO);

        assertNotNull(result, "El resultado no debe ser nulo.");
        assertEquals(user1DTO.getUsername(), result.getUsername());
        assertEquals(user1DTO.getEmail(), result.getEmail());

        verify(usersRepository, times(1)).save(user1);
        verify(modelMapper, times(1)).map(user1DTO, Users.class);
        verify(modelMapper, times(1)).map(user1, UsersDTO.class);
    }


    @Test
    void testGetUserById_ShouldThrowException_WhenUserNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> usersService.getUserById(1L),
                "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no existe");

        assertEquals("User Not Found", thrown.getMessage(), "El mensaje de la excepción no es el esperado.");
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser_ShouldThrowException_WhenUserNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> usersService.deleteUser(1L),
                "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no es encontrado");

        assertEquals("User Not Found", thrown.getMessage(), "El mensaje de la excepción no es el esperado.");
        verify(usersRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteUser_ShouldDeleteUser_WhenUserExists() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));

        usersService.deleteUser(1L);

        verify(usersRepository, times(1)).delete(user1);
    }
}
