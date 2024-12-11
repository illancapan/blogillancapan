package cl.llancapan.illancapan.util;

import cl.llancapan.illancapan.model.dto.UserDTO;
import cl.llancapan.illancapan.model.entity.Users;

public class MapperUtil {

    public static UserDTO toUserDTO(Users users) {
        if (users == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(users.getId());
        userDTO.setGithubId(users.getGithubId());
        userDTO.setEmail(users.getEmail());
        userDTO.setUsername(users.getUsername());
        userDTO.setAvatarUrl(users.getAvatarUrl());
        userDTO.setCreatedAt(users.getCreatedAt());
        userDTO.setUpdatedAt(users.getUpdatedAt());

        return userDTO;
    }
    public static Users toUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        Users users = new Users();
        users.setId(userDTO.getId());
        users.setGithubId(userDTO.getGithubId());
        users.setEmail(userDTO.getEmail());
        users.setUsername(userDTO.getUsername());
        users.setAvatarUrl(userDTO.getAvatarUrl());
        users.setCreatedAt(userDTO.getCreatedAt());
        users.setUpdatedAt(userDTO.getUpdatedAt());

        return users;
    }
}
