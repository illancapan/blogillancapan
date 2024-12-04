package cl.llancapan.illancapan.repository;

import cl.llancapan.illancapan.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
