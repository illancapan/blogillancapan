package cl.llancapan.illancapan.controller;


import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO createdUser = usersService.createUser(usersDTO);

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }
}
