package icesi.cmr.mappers;

import icesi.cmr.dto.CompanyDTO;
import icesi.cmr.model.relational.companies.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    @Autowired
    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO companyToCompanyDTO(Company company);

    Company companyDTOToCompany(CompanyDTO companyDTO);

}
