package icesi.cmr.model.relational.equipments;


import icesi.cmr.model.relational.companies.Department;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "contract")
public class Contract implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "start_date", nullable = false)
    private Long startDate;

    @Column(name = "end_date", nullable = false)
    private Long endDate;

    @Column(name = "monthly_cost", nullable = false)
    private Float monthlyCost;

    @Column(name = "contract_number", unique = true, nullable = false)
    private String contractNumber;

    private Boolean accepted = false;

    private Boolean isClosed = false;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "contract")
    private List<DeliveryCertificate> deliveryCertificate;
}
