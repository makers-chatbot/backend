package icesi.cmr.repositories.users;

import icesi.cmr.model.relational.users.UserRole;
import icesi.cmr.model.relational.users.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRolePK> {




}
