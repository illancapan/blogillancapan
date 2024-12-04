package cl.llancapan.illancapan.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long id;

    private String title;

    private String content;
    private String imageUrl;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    private List<CommentDTO> comments = new ArrayList<>();

    private List<ImageDTO> images = new ArrayList<>();
}
