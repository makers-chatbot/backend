package icesi.cmr.repositories.companies;

import icesi.cmr.model.relational.companies.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
