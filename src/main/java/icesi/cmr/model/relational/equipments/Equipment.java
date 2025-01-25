package icesi.cmr.model.relational.equipments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "equipment")
@Entity
public class Equipment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name= "inventary_code", unique = true, nullable = false)
    private String inventaryCode;

    @Column(nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "equipment")
    private List<DeliveryCertificate> deliveryCertificates;

}
