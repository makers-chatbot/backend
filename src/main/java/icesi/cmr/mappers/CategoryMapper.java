package icesi.cmr.mappers;

import icesi.cmr.dto.CategoryDTO;
import icesi.cmr.model.relational.equipments.EquipmentCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    EquipmentCategory toEntity(CategoryDTO dto);

}
