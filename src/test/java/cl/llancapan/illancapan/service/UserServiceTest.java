package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UserDTO;
import cl.llancapan.illancapan.model.entity.User;
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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Mock del repositorio
    @Mock
    private ModelMapper modelMapper; // Mock de ModelMapper

    @InjectMocks
    private UserService userService; // El servicio a probar

    private User user;  // Objeto de entidad que usaremos en las pruebas
    private UserDTO userDTO; // DTO correspondiente

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear una entidad User con todos los atributos necesarios
        user = new User(1L, "github-id", "username", "email@email.com", "avatar-url", LocalDateTime.now(), LocalDateTime.now());

        // Crear un UserDTO para comparación
        userDTO = new UserDTO(1L, "github-id", "username", "email@email.com", "avatar-url", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testGetUser() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));

        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.getUser(1L);

        assertNotNull(result, "El resultado no debe ser nulo, el servicio no debe devolver un UserDTO nulo");
        assertEquals(userDTO.getEmail(), result.getEmail(), "El email del resultado no coincide con el esperado");
        assertEquals(userDTO.getUsername(), result.getUsername(), "El nombre de usuario del resultado no coincide con el esperado");

        verify(userRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    void testGetUser_ShouldReturnUserDTO_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        UserDTO result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getUsername(), result.getUsername());
        assertEquals(userDTO.getEmail(), result.getEmail());

        verify(userRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(user, UserDTO.class);
    }

    @Test
    void testCreateUser_ShouldReturnUserDTO_WhenUserIsCreated() {
        // Crear el objeto User basado en el UserDTO
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());

        // Simular el comportamiento del repositorio para guardar el usuario
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Simular la conversión de UserDTO a User
        when(modelMapper.map(userDTO, User.class)).thenReturn(user);

        // Simular la conversión de User a UserDTO
        when(modelMapper.map(user, UserDTO.class)).thenReturn(userDTO);

        // Llamar al método que estamos probando
        UserDTO result = userService.createUser(userDTO);

        // Verificar que el resultado no sea null
        assertNotNull(result);
        assertEquals(userDTO.getUsername(), result.getUsername());
        assertEquals(userDTO.getEmail(), result.getEmail());

        // Verificar que el repositorio se haya llamado para guardar el usuario
        verify(userRepository, times(1)).save(any(User.class));
        verify(modelMapper, times(1)).map(userDTO, User.class); // Verifica el primer mapeo
        verify(modelMapper, times(1)).map(user, UserDTO.class); // Verifica el segundo mapeo
    }

    @Test
    void testGetUserById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userService.getUserById(1L), "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no existe");
        assertEquals("User Not Found", thrown.getMessage(), "El mensaje de la excepción no es el esperado. Esperábamos 'User Not Found'");
        verify(userRepository).findById(1L);
    }
    @Test
    void testDeleteUser_ShouldThrowException_WhenUserNotFound() {
        // Simulamos que el repositorio no encuentra al usuario
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificamos que se lance la excepción si no se encuentra al usuario
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> userService.deleteUser(1L),
                "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no es encontrado");

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("User Not Found", thrown.getMessage(),
                "El mensaje de la excepción no es el esperado. Esperábamos 'User Not Found'");

        // Verificamos que se haya llamado al repositorio
        verify(userRepository).findById(1L);
    }

    @Test
    void testDeleteUser_ShouldDeleteUser_WhenUserExists() {
        // Simulamos la presencia de un usuario en el repositorio
        User user = new User(1L, "githubId", "john_doe", "john@example.com", null, LocalDateTime.now(), LocalDateTime.now());
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Llamamos al método de eliminación
        userService.deleteUser(1L);

        // Verificamos que el repositorio elimine el usuario
        verify(userRepository).delete(user);
    }

}
