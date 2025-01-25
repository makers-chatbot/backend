package icesi.cmr.mappers;

import icesi.cmr.dto.CompanyDTO;
import icesi.cmr.dto.ContractDTO;
import icesi.cmr.model.relational.companies.Company;
import icesi.cmr.model.relational.equipments.Contract;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractMapper INSTANCE = Mappers.getMapper(ContractMapper.class);

    @Mappings({
            @Mapping(ignore = true, target = "deliveryCertificate"),
            @Mapping(ignore = true, target = "department"),
            @Mapping(ignore = true, target = "contractNumber")

    })
    Contract toContract(ContractDTO contractDTO);

    @Mappings({
            @Mapping(target =  "departmentId", source ="department.id"),
    })
    ContractDTO toContractDTO(Contract contract);

    // Add this method for partial updates
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateContractFromDto(ContractDTO dto, @MappingTarget Contract entity);

}
