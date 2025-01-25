package icesi.cmr.services.impl;

import icesi.cmr.dto.CompanyDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.CompanyMapper;
import icesi.cmr.model.relational.companies.Company;
import icesi.cmr.repositories.companies.CompanyRepository;
import icesi.cmr.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company getCompany(Integer id) {

        return companyRepository.findById(id).orElseThrow(() -> new EntityNotFound("Company not found"));
    }

    @Override
    public Company saveCompany(CompanyDTO companyDTO) {



        Company company = CompanyMapper.INSTANCE.companyDTOToCompany(companyDTO);
        company.setCreationDate(System.currentTimeMillis());

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Integer id) {


        if (!companyRepository.existsById(id)) {
            throw new EntityNotFound("Company not found");
        }
        companyRepository.deleteById(id);

    }

    @Override
    public Company updateCompany(CompanyDTO company) {
        return null;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
