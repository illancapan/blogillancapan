package cl.llancapan.illancapan.controller;


import cl.llancapan.illancapan.model.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


    @PostMapping
    public UserDTO createUser(UserDTO userDTO) {

        return new UserDTO(
                1L,
                "github-id",
                "username",
                "email@email.com",
                "avatar-url",
                null,
                null
        );

    }
}
