package icesi.cmr.repositories.companies;

import icesi.cmr.model.relational.companies.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {



}
