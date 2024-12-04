package cl.llancapan.illancapan.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String githubId;
    private String username;
    private String email;
    private String avatarUrl;
    private LocalDateTime createdAt = LocalDateTime.now();

}
