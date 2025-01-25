package icesi.cmr.services.impl;

import icesi.cmr.dto.DepartmentDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.DepartmentMapper;
import icesi.cmr.model.relational.companies.Department;
import icesi.cmr.repositories.companies.CompanyRepository;
import icesi.cmr.repositories.companies.DepartmentRepository;
import icesi.cmr.services.interfaces.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Department getDepartment(Integer id) {

       return departmentRepository.findById(id).orElseThrow( () -> new EntityNotFound("Department not found"));

    }

    @Override

    public Department saveDepartment(DepartmentDTO department) {


        System.out.println("DepartmentDTO to save on service: " + department);

        Department departmentToSave = DepartmentMapper.INSTANCE.departmentDTOToDepartment(department);

        departmentToSave.setCompany(companyRepository.findById(department.getCompanyId()).orElseThrow( () -> new EntityNotFound("Company not found on department")));

        return departmentRepository.save(departmentToSave);
    }

    @Override
    public void deleteDepartment(Integer id) {

        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
        } else {
            throw new EntityNotFound("Department not found");
        }


    }

    @Override
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }
}
