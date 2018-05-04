package PGL;

import org.springframework.data.repository.CrudRepository;
import PGL.User;

/**
 * Spring Boot creates the repository automatically, no code needed.
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
