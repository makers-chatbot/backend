package icesi.cmr.mappers;

import icesi.cmr.dto.UserDTO;
import icesi.cmr.model.relational.users.Admin;
import icesi.cmr.model.relational.users.Client;
import icesi.cmr.model.relational.users.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {


    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({

            @Mapping(source = "company.id", target = "companyId"),
            @Mapping(source = "roles", target = "rolesNames"),
            @Mapping(source = "department.id", target = "departmentId"),
            @Mapping(target = "password", ignore = true),
    })
    UserDTO userToUserDTO(Client user);

    @Mappings({

            @Mapping(source = "roles", target = "rolesNames"),

    })
    UserDTO userToUserDTO(Admin user);


    Client userDTOToUser(UserDTO userDTO);

    //Mapstruct infer that he should use this method to generate roles list.

    default List<String> mapRoles(List<UserRole> userRoles) {
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toList());
    }


}
