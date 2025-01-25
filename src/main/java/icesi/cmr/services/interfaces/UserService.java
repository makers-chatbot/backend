package icesi.cmr.services.interfaces;

import icesi.cmr.dto.UserDTO;
import icesi.cmr.dto.products.ProductDTO;
import icesi.cmr.exceptions.ProductTypeRequiredException;
import icesi.cmr.model.relational.users.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserByUsername(String username);

    User save(UserDTO user);

    User getUserById(Integer id);

    List<User> getAllUsers();
}
