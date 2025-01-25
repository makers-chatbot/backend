package icesi.cmr.services.impl;

import icesi.cmr.dto.UserDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.exceptions.NotAllowGetAdminInfoException;
import icesi.cmr.exceptions.NotValidUserRoleCreation;
import icesi.cmr.mappers.UserMapper;
import icesi.cmr.model.relational.companies.Company;
import icesi.cmr.model.relational.companies.Department;
import icesi.cmr.model.relational.users.*;
import icesi.cmr.repositories.companies.CompanyRepository;
import icesi.cmr.repositories.companies.DepartmentRepository;
import icesi.cmr.repositories.users.RoleRepository;
import icesi.cmr.repositories.users.UserRepository;
import icesi.cmr.repositories.users.UserRoleRepository;
import icesi.cmr.services.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RoleRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public User getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new EntityNotFound("User not found");
        }

        return user;
    }

    public User getUserById(Integer id) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFound("User not found"));

        //Verify if user is admin

        for (UserRole userRole : user.getRoles()) {

            if (userRole.getRole().getName().equals("ROLE_ADMIN")) {
                throw new NotAllowGetAdminInfoException("Admin info is not allowed to be retrieved");
            }
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {

        //filter user admins

        List<User> users = userRepository.findAll().stream().filter(user -> {


            if (user instanceof Admin) {
                return false;
            }
            return true;
        }).collect(Collectors.toList());

        return users;
    }


    @Override
    public User save(UserDTO user) {

        System.out.println("User on user service: " + user + "\n");

        if (user.getRolesNames().contains("ADMIN")) {

            throw new NotValidUserRoleCreation("Admin role is not allowed to be created");

        }

        Client userToSave = userMapper.userDTOToUser(user);

        System.out.println("Client parent properties: "
                + "| password:" + userToSave.getPassword()
                + " | username: " + userToSave.getUsername() + " | email: "
                + userToSave.getEmail() + " | id: "
                + userToSave.getId()
                + " | roles:" + userToSave.getRoles());
        System.out.println("Client mapper used: " + userToSave);

        //Get parent attributes from Client cast


        //Created at process

        Long createdAt = System.currentTimeMillis();

        //Get company

        Company company = companyRepository.findById(user.getCompanyId()).orElseThrow(() -> new EntityNotFound("Company not found"));

        //Get department

        Department department = departmentRepository.findById(user.getDepartmentId()).orElseThrow(() -> new EntityNotFound("Department not found"));

        //Set properties

        userToSave.setCompany(company);

        userToSave.setDepartment(department);

        userToSave.setCreatedAt(createdAt);

        userToSave.setPassword(encoder.encode(userToSave.getPassword()));

        if (userToSave.getRoles() == null) {
            userToSave.setRoles(new ArrayList<>());
        }

        userRepository.save(userToSave);


        //Get roles

        Set<Role> roles = user.getRolesNames().stream().map(roleName -> {

            Role role = roleRepository.findByName(roleName);

            if (role == null) {
                throw new EntityNotFound("Role not found");
            }

            return role;
        }).collect(Collectors.toSet());

        for (Role role : roles) {

            UserRolePK userRolePK = new UserRolePK(user.getId(), role.getId());


            UserRole userRole = new UserRole(userRolePK, userToSave, role);


            userRoleRepository.save(userRole);

            userToSave.getRoles().add(userRole);
            System.out.println("User role to save: " + role.getName());

        }


        System.out.println("Roles on user service: " + userToSave.getRoles());


        System.out.println("Finished for user: " + userToSave.getUsername());
        return userRepository.findById(userToSave.getId()).orElseThrow(() -> new EntityNotFound("User not found"));

    }


}
