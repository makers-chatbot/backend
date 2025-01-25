package icesi.cmr.model.relational.users;

import icesi.cmr.model.relational.companies.Company;
import icesi.cmr.model.relational.companies.Department;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Client extends User implements Serializable  {

    //----------------Relations----------------

    @ManyToOne
    private Company company;

    @ManyToOne
    private Department department;

}
