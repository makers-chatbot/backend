package icesi.cmr.services.impl;

import icesi.cmr.dto.ContractDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.ContractMapper;
import icesi.cmr.model.relational.companies.Department;
import icesi.cmr.model.relational.equipments.Contract;
import icesi.cmr.repositories.companies.CompanyRepository;
import icesi.cmr.repositories.companies.DepartmentRepository;
import icesi.cmr.repositories.equipments.ContractRepository;
import icesi.cmr.services.interfaces.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.server.UID;
import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Override
    public void createContract(ContractDTO contractDTO) {

        Department department = departmentRepository
                .findById(contractDTO.getDepartmentId())
                .orElseThrow(() -> new EntityNotFound("Department not found"));

        Contract contract = ContractMapper.INSTANCE.toContract(contractDTO);
        contract.setContractNumber(new UID().toString());
        contract.setDepartment(department);

        contractRepository.save(contract);

    }

    @Override
    public void updateContract(ContractDTO contractDTO) {


        Contract existingContract = contractRepository.findById(contractDTO.getId())
                .orElseThrow(() -> new EntityNotFound("Contract not found"));

        // Use the mapper to update the entity
        ContractMapper.INSTANCE.updateContractFromDto(contractDTO, existingContract);

        // Handle department separately if needed
        if (contractDTO.getDepartmentId() != null) {
            Department department = departmentRepository.findById(contractDTO.getDepartmentId())
                    .orElseThrow(() -> new EntityNotFound("Department not found"));
            existingContract.setDepartment(department);
        }

        contractRepository.save(existingContract);

    }

    @Override
    public void deleteContract(Integer id) {

        contractRepository.deleteById(id);

    }

    @Override
    public List<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Contract getContractById(Integer id) {
        return contractRepository.findById(id).orElseThrow(() -> new EntityNotFound("Contract not found"));
    }

    @Override
    public List<Contract> getContractsByDepartmentId(Integer departmentId) {
        return contractRepository.findByDepartmentId(departmentId);
    }
}
