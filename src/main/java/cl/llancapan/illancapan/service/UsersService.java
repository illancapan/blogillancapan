package cl.llancapan.illancapan.service;

import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.model.entity.Users;
import cl.llancapan.illancapan.repository.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UsersDTO getUser(Long userId) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        return modelMapper.map(users, UsersDTO.class);
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
