package cl.llancapan.illancapan.controller;


import cl.llancapan.illancapan.model.dto.UserDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    public UserDTO createUser(UserDTO userDTO) {
        return null;
    }
}
