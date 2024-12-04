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
public class CommentDTO {

    private Long id;

    private String content;
    private UserDTO author;
    private PostDTO post;
    private LocalDateTime createdAt = LocalDateTime.now();
}
