package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.model.entity.Users;
import cl.llancapan.illancapan.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UsersDTO createUser(UsersDTO usersDTO) {
        Users users = modelMapper.map(usersDTO, Users.class);
        users = usersRepository.save(users);

        return modelMapper.map(users, UsersDTO.class);
    }

    public List<UsersDTO> getUserAll() {
        List<Users> userList =  usersRepository.findAll();

        return userList.stream()
                .map(users ->  modelMapper.map(users, UsersDTO.class))
                .collect(Collectors.toList());
    }

    public UsersDTO getUserById(Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return modelMapper.map(users, UsersDTO.class);
    }

    public void deleteUser(long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        usersRepository.delete(users);
    }


}
