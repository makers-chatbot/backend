package icesi.cmr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {


    private String username;

    private String email;

    private String password;

    private Integer id;

    private List<String> rolesNames;

    private Integer companyId;

    private Integer departmentId;

}
