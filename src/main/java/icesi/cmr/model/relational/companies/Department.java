package icesi.cmr.model.relational.companies;


import icesi.cmr.model.relational.equipments.Contract;
import icesi.cmr.model.relational.users.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "department")
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "department")
    private List<Client> users;

    @OneToMany(mappedBy = "department")
    private List<Contract> contracts;


    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", company=" + company +
                '}';
    }
}
