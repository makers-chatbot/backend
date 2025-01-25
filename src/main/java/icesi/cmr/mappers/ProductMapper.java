package icesi.cmr.mappers;

import icesi.cmr.dto.products.LaptopDTO;
import icesi.cmr.dto.products.PhoneDTO;
import icesi.cmr.dto.products.PrinterDTO;
import icesi.cmr.dto.products.ProductDTO;
import icesi.cmr.model.noRelational.products.Laptop;
import icesi.cmr.model.noRelational.products.Phone;
import icesi.cmr.model.noRelational.products.Printer;
import icesi.cmr.model.noRelational.products.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE  = Mappers.getMapper(ProductMapper.class);

    // Subclass mapping methods
    Laptop toEntity(LaptopDTO dto);

    Printer toEntity(PrinterDTO dto);

    Phone toEntity(PhoneDTO dto);

    void updateProductFromDTO(ProductDTO dto, @MappingTarget Product product);

    default Product toEntity(ProductDTO dto) {
        if (dto instanceof LaptopDTO laptopDTO) {
            Laptop laptop = toEntity(laptopDTO);
            updateProductFromDTO(dto, laptop);
            return laptop;
        } else if (dto instanceof PrinterDTO printerDTO) {
            Printer printer = toEntity(printerDTO);
            updateProductFromDTO(dto, printer);
            return printer;
        } else if (dto instanceof PhoneDTO phoneDTO) {
            Phone phone = toEntity(phoneDTO);
            updateProductFromDTO(dto, phone);
            return phone;
        }
        throw new IllegalArgumentException("Unknown product type: " + dto.getClass());
    }
}

