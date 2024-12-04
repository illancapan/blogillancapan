package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UserDTO;
import cl.llancapan.illancapan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO getUser(UserDTO userId){
        UserDTO user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        return new UserDTO(user.getUsername());
    }
}
