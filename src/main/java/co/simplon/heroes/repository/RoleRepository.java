package co.simplon.heroes.repository;

import co.simplon.heroes.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
