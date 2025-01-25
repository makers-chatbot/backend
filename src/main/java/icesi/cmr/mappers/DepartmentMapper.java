package icesi.cmr.mappers;


import icesi.cmr.dto.DepartmentDTO;
import icesi.cmr.model.relational.companies.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {


    DepartmentMapper INSTANCE = Mappers.getMapper(DepartmentMapper.class);


    @Mappings({
            @Mapping(ignore = true, target = "company"),
            @Mapping(ignore = true, target = "id"),
    })
    Department departmentDTOToDepartment(DepartmentDTO departmentDTO);

    @Mappings({
            @Mapping(source = "company.id", target = "companyId"),
    })
    DepartmentDTO departmentToDepartmentDTO(Department department);


}
