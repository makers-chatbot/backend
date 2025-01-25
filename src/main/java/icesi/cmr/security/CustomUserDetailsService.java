package icesi.cmr.security;

import icesi.cmr.model.relational.users.Client;
import icesi.cmr.model.relational.users.User;
import icesi.cmr.repositories.users.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) throw new UsernameNotFoundException("User 404");


        Set<String> roles = user.getRoles().stream().map(userRole -> userRole.getRole().getName()).collect(Collectors.toSet());

        return new UserPrincipal( roles, user);

    }
}
