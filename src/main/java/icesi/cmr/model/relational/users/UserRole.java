package icesi.cmr.model.relational.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "user_role")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole implements Serializable {

    @EmbeddedId
    private UserRolePK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;










}
