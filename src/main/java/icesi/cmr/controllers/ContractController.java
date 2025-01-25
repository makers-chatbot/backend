package icesi.cmr.controllers;

import icesi.cmr.dto.ContractDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.ContractMapper;
import icesi.cmr.model.relational.equipments.Contract;
import icesi.cmr.services.interfaces.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
public class ContractController {


    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<String> createContract(@RequestBody ContractDTO contractDTO) {
        try {

            contractService.createContract(contractDTO);
            return ResponseEntity.ok("Contract created");
        } catch (EntityNotFound e) {

            e.printStackTrace();
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDTO> getContractById(@PathVariable Integer id) {
        try {
            Contract contract = contractService.getContractById(id);
            ContractDTO contractDTO = ContractMapper.INSTANCE.toContractDTO(contract);
            return ResponseEntity.ok(contractDTO);
        } catch (EntityNotFound e) {

            e.printStackTrace();
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping
    public ResponseEntity<List<ContractDTO>> getContracts(@RequestParam(required = false) Integer departmentId) {

        try {

            List<ContractDTO> contractDTOS;

            if (departmentId != null) {
                contractDTOS = contractService.getContractsByDepartmentId(departmentId)
                        .stream()
                        .map(ContractMapper.INSTANCE::toContractDTO)
                        .toList();
                return ResponseEntity.ok(contractDTOS);
            }

            contractDTOS = contractService.getAllContracts()
                    .stream()
                    .map(ContractMapper.INSTANCE::toContractDTO)
                    .toList();
            return ResponseEntity.ok(contractDTOS);
        } catch (EntityNotFound e) {

            e.printStackTrace();
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    @PatchMapping("/{id}")
    public ResponseEntity<String> updateContract(@PathVariable Integer id, @RequestBody ContractDTO contractDTO) {
        try {
            contractDTO.setId(id);
            contractService.updateContract(contractDTO);
            return ResponseEntity.ok("Contract updated");
        } catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
