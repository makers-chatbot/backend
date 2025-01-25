package icesi.cmr.controllers;

import icesi.cmr.dto.AuthRequest;
import icesi.cmr.dto.AuthResponseDTO;
import icesi.cmr.dto.UserDTO;
import icesi.cmr.mappers.UserMapper;
import icesi.cmr.model.relational.users.Admin;
import icesi.cmr.model.relational.users.Client;
import icesi.cmr.model.relational.users.User;
import icesi.cmr.security.JWT.JwtService;
import icesi.cmr.services.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                            authRequest.getPassword()));


            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwtToken = jwtService.generateToken(authRequest.getUsername());


            User user = userService.getUserByUsername(authRequest.getUsername());

            //Need to cast based on the user type

            UserDTO userResponse = null;

            if (user instanceof Admin) {

                userResponse = UserMapper.INSTANCE.userToUserDTO((Admin) user);

            } else {

                userResponse = UserMapper.INSTANCE.userToUserDTO((Client) user);

            }

            AuthResponseDTO authResponseDTO = AuthResponseDTO.builder().userDTO(userResponse).token(jwtToken).build();

            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);

        } catch (AuthenticationException e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }


    }


}
