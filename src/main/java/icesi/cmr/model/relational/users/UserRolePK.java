package icesi.cmr.model.relational.users;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRolePK implements Serializable {

    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "role_id", insertable = false, updatable = false)
    private Integer roleId;

}
