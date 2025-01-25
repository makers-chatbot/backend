package icesi.cmr.controllers;


import icesi.cmr.dto.UserDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.exceptions.NotAllowGetAdminInfoException;
import icesi.cmr.exceptions.NotValidUserRoleCreation;
import icesi.cmr.mappers.UserMapper;
import icesi.cmr.model.relational.users.Client;
import icesi.cmr.model.relational.users.User;
import icesi.cmr.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) {

        try {

            User user = userService.save(userDTO);

            System.out.println("User after got after post: "+ user);

            return ResponseEntity.ok(UserMapper.INSTANCE.userToUserDTO((Client) user));

        }catch (NotValidUserRoleCreation     | EntityNotFound e) {
            e.printStackTrace();

            return ResponseEntity.notFound().build();
        }catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.internalServerError().build();

        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id) {

        try {

            UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO((Client) userService.getUserById(id));

            return ResponseEntity.ok(userDTO);

        }catch (  EntityNotFound e) {
            e.printStackTrace();

            return ResponseEntity.notFound().build();
        }catch (NotAllowGetAdminInfoException e) {
            e.printStackTrace();

            return ResponseEntity.status(403).build();
        }

        catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.internalServerError().build();

        }
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDTO>> getUsers() {

        try {

            List<UserDTO> users = userService.getAllUsers().stream().map(user -> UserMapper.INSTANCE.userToUserDTO( (Client) user)).collect(Collectors.toList());

            return ResponseEntity.ok(users);

        }catch (EntityNotFound e) {
            e.printStackTrace();

            return ResponseEntity.notFound().build();
        }catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.internalServerError().build();

        }
    }



}
