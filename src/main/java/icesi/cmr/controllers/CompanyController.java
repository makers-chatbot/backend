package icesi.cmr.controllers;

import icesi.cmr.dto.CompanyDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.CompanyMapper;
import icesi.cmr.model.relational.companies.Company;
import icesi.cmr.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyDTO> saveCompany(@RequestBody CompanyDTO companyDTO) {


        try {

            Company company = companyService.saveCompany(companyDTO);
            System.out.println("Company after got after post: " + company);
            return ResponseEntity.ok(CompanyMapper.INSTANCE.companyToCompanyDTO(company));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('BUSINESS_MANAGER')")
    public ResponseEntity<CompanyDTO> getCompany(@PathVariable Integer id) {

        try {

            Company company = companyService.getCompany(id);

            return ResponseEntity.ok(CompanyMapper.INSTANCE.companyToCompanyDTO(company));

        } catch (EntityNotFound e) {
            e.printStackTrace();

            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BUSINESS_MANAGER')")
    public ResponseEntity<?> deleteCompany(@PathVariable Integer id) {

        try {

            companyService.deleteCompany(id);

            return ResponseEntity.ok().build();

        } catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {

        try {

            List<CompanyDTO> companyDTOList = companyService.getAllCompanies().stream()
                    .map(CompanyMapper.INSTANCE::companyToCompanyDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(companyDTOList);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}
