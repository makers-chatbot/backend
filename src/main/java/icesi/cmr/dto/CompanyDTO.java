package icesi.cmr.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private Integer id;

    private String nit;

    private String name;

    private String industry;

    private String address;

    private String phone;

    private String email;

    private String country;

    private String state;

}
