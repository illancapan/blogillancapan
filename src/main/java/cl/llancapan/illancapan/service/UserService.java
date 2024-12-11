package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UserDTO;
import cl.llancapan.illancapan.model.entity.Users;
import cl.llancapan.illancapan.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public UserDTO createUser(UserDTO userDTO) {
        Users user = modelMapper.map(userDTO, Users.class); // De DTO a entidad
        Users savedUser = userRepository.save(user);      // Guardar entidad
        return modelMapper.map(savedUser, UserDTO.class); // De entidad a DTO
    }

    public List<UserDTO> getUserAll() {
        List<Users> userList =  userRepository.findAll();

        return userList.stream()
                .map(users ->  modelMapper.map(users, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return modelMapper.map(users, UserDTO.class);
    }

    public void deleteUser(long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        userRepository.delete(users);
    }


}
