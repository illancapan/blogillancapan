package cl.llancapan.illancapan.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;
    private String imageUrl;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
}
