package icesi.cmr.repositories.users;

import icesi.cmr.model.relational.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {



    Role findByName(String name);
}
