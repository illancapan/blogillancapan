package cl.llancapan.illancapan.controller;


import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        try {
            UsersDTO createdUser = usersService.createUser(usersDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo de excepciones
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUser() {
        try {
            List<UsersDTO> users = usersService.getUserAll();
            return new ResponseEntity<>(users, HttpStatus.OK); // Devuelve la lista con OK
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error en la obtención de usuarios
        }
    }

}
