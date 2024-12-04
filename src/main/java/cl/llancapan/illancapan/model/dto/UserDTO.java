package cl.llancapan.illancapan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String githubId;
    private String username;
    private String email;
    private String avatarUrl;
    private LocalDateTime createdAt = LocalDateTime.now();

}
