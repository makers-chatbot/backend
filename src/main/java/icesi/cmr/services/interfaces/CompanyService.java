package icesi.cmr.services.interfaces;

import icesi.cmr.dto.CompanyDTO;
import icesi.cmr.model.relational.companies.Company;

import java.util.List;

public interface CompanyService {

    Company getCompany(Integer id);

    Company saveCompany(CompanyDTO company);

    void deleteCompany(Integer id);

    Company updateCompany(CompanyDTO company);

    List<Company> getAllCompanies();


}
