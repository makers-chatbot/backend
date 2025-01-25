package icesi.cmr.mappers;

import icesi.cmr.dto.DeliveryCertificateDTO;
import icesi.cmr.model.relational.equipments.DeliveryCertificate;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeliveryCertificateMapper {


    DeliveryCertificateMapper INSTANCE = Mappers.getMapper(DeliveryCertificateMapper.class);


    @Mappings({

            @Mapping(target = "contractId", source ="contract.id" ),
            @Mapping(target = "equipmentId", source ="equipment.id" ),
            @Mapping(target = "equipmentInventaryCode", source ="equipment.inventaryCode" )

    })

    DeliveryCertificateDTO toDTO(DeliveryCertificate deliveryCertificate);


    @Mappings({
            @Mapping(ignore = true, target = "contract"),
            @Mapping(ignore = true, target = "equipment"),
            @Mapping(ignore = true, target = "id")
    })
    DeliveryCertificate toEntity(DeliveryCertificateDTO deliveryCertificateDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeliveryCertificateFromDto(DeliveryCertificateDTO dto, @MappingTarget DeliveryCertificate entity);
}
