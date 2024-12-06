package cl.llancapan.illancapan.util;

import cl.llancapan.illancapan.model.dto.UsersDTO;
import cl.llancapan.illancapan.model.entity.Users;

public class MapperUtil {

    public static UsersDTO toUserDTO(Users users) {
        if (users == null) {
            return null;
        }

        UsersDTO usersDTO = new UsersDTO();
        usersDTO.setId(users.getId());
        usersDTO.setGithubId(users.getGithubId());
        usersDTO.setEmail(users.getEmail());
        usersDTO.setUsername(users.getUsername());
        usersDTO.setAvatarUrl(users.getAvatarUrl());
        usersDTO.setCreatedAt(users.getCreatedAt());
        usersDTO.setUpdatedAt(users.getUpdatedAt());

        return usersDTO;
    }
    public static Users toUser(UsersDTO usersDTO) {
        if (usersDTO == null) {
            return null;
        }

        Users users = new Users();
        users.setId(usersDTO.getId());
        users.setGithubId(usersDTO.getGithubId());
        users.setEmail(usersDTO.getEmail());
        users.setUsername(usersDTO.getUsername());
        users.setAvatarUrl(usersDTO.getAvatarUrl());
        users.setCreatedAt(usersDTO.getCreatedAt());
        users.setUpdatedAt(usersDTO.getUpdatedAt());

        return users;
    }
}
