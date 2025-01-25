package icesi.cmr.model.relational.companies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icesi.cmr.model.relational.users.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Table(name = "company")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Company implements Serializable {


    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nit;

    @Column(unique = true, nullable = false)
    private String name;

    private String industry;

    private String address;

    @Column(unique = true, nullable = false)
    private String phone;

    @Column(unique = true, nullable = false)
    private String email;

    private String country;

    private String state;

    @Column(name = "creation_date", nullable = false)
    private Long creationDate;

    //----------------Relations----------------

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Client> users;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Department> departments;


    @Override
    public String toString() {
        return "Company{" +
                "creationDate=" + creationDate +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", industry='" + industry + '\'' +
                ", name='" + name + '\'' +
                ", nit='" + nit + '\'' +
                ", id=" + id +
                '}';
    }
}
