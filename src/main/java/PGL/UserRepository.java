package PGL;

import org.springframework.data.repository.CrudRepository;
import PGL.User;

public interface UserRepository extends CrudRepository<User, Long> {

}