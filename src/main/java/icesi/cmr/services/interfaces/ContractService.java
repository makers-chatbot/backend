package icesi.cmr.services.interfaces;

import icesi.cmr.dto.ContractDTO;
import icesi.cmr.model.relational.equipments.Contract;

import java.util.List;

public interface ContractService {

    void createContract(ContractDTO contractDTO);

    void updateContract(ContractDTO contractDTO);

    void deleteContract(Integer id);

    List<Contract> getAllContracts();

    Contract getContractById(Integer id);

    List<Contract> getContractsByDepartmentId(Integer departmentId);


}
