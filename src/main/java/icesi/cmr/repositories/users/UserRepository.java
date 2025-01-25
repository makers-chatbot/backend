package icesi.cmr.repositories.users;

import icesi.cmr.model.relational.users.Client;
import icesi.cmr.model.relational.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);



}
