package cl.llancapan.illancapan.repository;

import cl.llancapan.illancapan.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
