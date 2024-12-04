package cl.llancapan.illancapan.repository;

import cl.llancapan.illancapan.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
