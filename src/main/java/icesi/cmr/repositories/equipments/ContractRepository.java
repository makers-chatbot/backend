package icesi.cmr.repositories.equipments;

import icesi.cmr.model.relational.equipments.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {


    List<Contract> findByDepartmentId(Integer departmentId);
}
