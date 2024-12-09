package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.model.entity.Users;
import cl.llancapan.illancapan.repository.UsersRepository;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository; // Mock del repositorio
    @Mock
    private ModelMapper modelMapper; // Mock de ModelMapper

    @InjectMocks
    private UsersService usersService; // El servicio a probar

    private MockMvc mockMvc;

//    private Users users;  // Objeto de entidad que usaremos en las pruebas

    private Users user1;
    private Users user2;
    private List<Users> usersListEntities; // Usamos entidades Users

    private UsersDTO user1DTO;
    private UsersDTO user2DTO;
    private List<UsersDTO> usersListDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user1 = new Users(
                1L,
                "github-id",
                "username",
                "email@email.com",
                "avatar-url",
                LocalDateTime.now(),
                LocalDateTime.now());

        user2 = new Users(
                2L,
                "github-id",
                "username",
                "email@email.com",
                "avatar-url",
                LocalDateTime.now(),
                LocalDateTime.now());

        usersListEntities = Arrays.asList(user1, user2);

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

        usersListDTO = Arrays.asList(user1DTO, user2DTO);

        mockMvc = MockMvcBuilders.standaloneSetup(usersService).build();

        when(usersRepository.findAll()).thenReturn(usersListEntities);

        when(modelMapper.map(user1, UsersDTO.class)).thenReturn(user1DTO);
        when(modelMapper.map(user2, UsersDTO.class)).thenReturn(user2DTO);
    }

    @Test
    void testGetAllUser_shouldGetAllUsers() {
        List<UsersDTO> result = usersService.getUserAll();

        assertNotNull(result, "El resultado no debe ser nulo");
        assertEquals(2, result.size(), "El numero de usuairios obtenidos no es el esperado ");
        assertEquals(user1DTO.getEmail(), result.get(0).getEmail(), "El email del primer usuario no coincide");
        assertEquals(user2DTO.getEmail(), result.get(1).getEmail(), "El email del segundo usuario no coincide");

        verify(usersRepository, times(1)).findAll();
        verify(modelMapper, times(1)).map(user1, UsersDTO.class);
        verify(modelMapper, times(1)).map(user2, UsersDTO.class);
    }


    @Test
    void testGetUser_ShouldReturnUserDTO_WhenUserExists() {
        when(usersRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(modelMapper.map(user1, UsersDTO.class)).thenReturn(user1DTO);

        UsersDTO result = usersService.getUserById(1L);

        assertNotNull(result);
        assertEquals(user1DTO.getId(), result.getId());
        assertEquals(user1DTO.getUsername(), result.getUsername());
        assertEquals(user1DTO.getEmail(), result.getEmail());

        verify(usersRepository, times(1)).findById(1L);
        verify(modelMapper, times(1)).map(user1, UsersDTO.class);
    }

    @Test
    void testCreateUser_ShouldReturnUserDTO_WhenUserIsCreated() {

        when(usersRepository.save(any(Users.class))).thenReturn(user1);

        when(modelMapper.map(user1DTO, Users.class)).thenReturn(user1);

        // Simular la conversión de User a UserDTO
        when(modelMapper.map(user1, UsersDTO.class)).thenReturn(user1DTO);

        // Llamar al método que estamos probando
        UsersDTO result = usersService.createUser(user1DTO);

        // Verificar que el resultado no sea null
        assertNotNull(result);
        assertEquals(user1DTO.getUsername(), result.getUsername());
        assertEquals(user1DTO.getEmail(), result.getEmail());

        // Verificar que el repositorio se haya llamado para guardar el usuario
        verify(usersRepository, times(1)).save(any(Users.class));
        verify(modelMapper, times(1)).map(user1DTO, Users.class); // Verifica el primer mapeo
        verify(modelMapper, times(1)).map(user1, UsersDTO.class); // Verifica el segundo mapeo
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
