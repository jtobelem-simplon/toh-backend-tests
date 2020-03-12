package co.simplon.heroes.repository;

import co.simplon.heroes.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
