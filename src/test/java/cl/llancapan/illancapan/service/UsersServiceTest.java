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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository; // Mock del repositorio
    @Mock
    private ModelMapper modelMapper; // Mock de ModelMapper

    @InjectMocks
    private UsersService usersService; // El servicio a probar

    private Users users;  // Objeto de entidad que usaremos en las pruebas
    private UsersDTO usersDTO; // DTO correspondiente

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crear una entidad User con todos los atributos necesarios
        users = new Users(1L, "github-id", "username", "email@email.com", "avatar-url", LocalDateTime.now(), LocalDateTime.now());

        // Crear un UserDTO para comparación
        usersDTO = new UsersDTO(1L, "github-id", "username", "email@email.com", "avatar-url", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void testGetUser() {
        when(usersRepository.findById(1L)).thenReturn(java.util.Optional.of(users));

        when(modelMapper.map(users, UsersDTO.class)).thenReturn(usersDTO);

        UsersDTO result = usersService.getUser(1L);

        assertNotNull(result, "El resultado no debe ser nulo, el servicio no debe devolver un UserDTO nulo");
        assertEquals(usersDTO.getEmail(), result.getEmail(), "El email del resultado no coincide con el esperado");
        assertEquals(usersDTO.getUsername(), result.getUsername(), "El nombre de usuario del resultado no coincide con el esperado");

        verify(usersRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(users, UsersDTO.class);
    }

    @Test
    void testGetUser_ShouldReturnUserDTO_WhenUserExists() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));
        when(modelMapper.map(users, UsersDTO.class)).thenReturn(usersDTO);

        UsersDTO result = usersService.getUser(1L);

        assertNotNull(result);
        assertEquals(usersDTO.getId(), result.getId());
        assertEquals(usersDTO.getUsername(), result.getUsername());
        assertEquals(usersDTO.getEmail(), result.getEmail());

        verify(usersRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(users, UsersDTO.class);
    }

    @Test
    void testCreateUser_ShouldReturnUserDTO_WhenUserIsCreated() {
        // Crear el objeto User basado en el UserDTO
        Users users = new Users();
        users.setUsername(usersDTO.getUsername());
        users.setEmail(usersDTO.getEmail());

        // Simular el comportamiento del repositorio para guardar el usuario
        when(usersRepository.save(any(Users.class))).thenReturn(users);

        // Simular la conversión de UserDTO a User
        when(modelMapper.map(usersDTO, Users.class)).thenReturn(users);

        // Simular la conversión de User a UserDTO
        when(modelMapper.map(users, UsersDTO.class)).thenReturn(usersDTO);

        // Llamar al método que estamos probando
        UsersDTO result = usersService.createUser(usersDTO);

        // Verificar que el resultado no sea null
        assertNotNull(result);
        assertEquals(usersDTO.getUsername(), result.getUsername());
        assertEquals(usersDTO.getEmail(), result.getEmail());

        // Verificar que el repositorio se haya llamado para guardar el usuario
        verify(usersRepository, times(1)).save(any(Users.class));
        verify(modelMapper, times(1)).map(usersDTO, Users.class); // Verifica el primer mapeo
        verify(modelMapper, times(1)).map(users, UsersDTO.class); // Verifica el segundo mapeo
    }

    @Test
    void testGetUserById_ShouldThrowException_WhenUserNotFound() {
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usersService.getUserById(1L), "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no existe");
        assertEquals("User Not Found", thrown.getMessage(), "El mensaje de la excepción no es el esperado. Esperábamos 'User Not Found'");
        verify(usersRepository).findById(1L);
    }
    @Test
    void testDeleteUser_ShouldThrowException_WhenUserNotFound() {
        // Simulamos que el repositorio no encuentra al usuario
        when(usersRepository.findById(1L)).thenReturn(Optional.empty());

        // Verificamos que se lance la excepción si no se encuentra al usuario
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> usersService.deleteUser(1L),
                "Se esperaba que se lanzara una excepción RuntimeException cuando el usuario no es encontrado");

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals("User Not Found", thrown.getMessage(),
                "El mensaje de la excepción no es el esperado. Esperábamos 'User Not Found'");

        // Verificamos que se haya llamado al repositorio
        verify(usersRepository).findById(1L);
    }

    @Test
    void testDeleteUser_ShouldDeleteUser_WhenUserExists() {
        // Simulamos la presencia de un usuario en el repositorio
        Users users = new Users(1L, "githubId", "john_doe", "john@example.com", null, LocalDateTime.now(), LocalDateTime.now());
        when(usersRepository.findById(1L)).thenReturn(Optional.of(users));

        // Llamamos al método de eliminación
        usersService.deleteUser(1L);

        // Verificamos que el repositorio elimine el usuario
        verify(usersRepository).delete(users);
    }

}
