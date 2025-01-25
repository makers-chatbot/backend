package icesi.cmr.controllers;

import icesi.cmr.dto.DepartmentDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.DepartmentMapper;
import icesi.cmr.model.relational.companies.Department;
import icesi.cmr.services.interfaces.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping()
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {

        try {
            List<DepartmentDTO> departmentDTOList = departmentService.getDepartments().stream()
                    .map(department -> DepartmentMapper.INSTANCE.departmentToDepartmentDTO(department)).toList();

            return ResponseEntity.ok(departmentDTOList);
        }catch (EntityNotFound e){
            e.printStackTrace();

            return  ResponseEntity.notFound().build();

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        System.out.println("DepartmentDTO to save on service: " + departmentDTO);
        try {
            Department dep =  departmentService.saveDepartment(departmentDTO);
            DepartmentDTO depDTO = DepartmentMapper.INSTANCE.departmentToDepartmentDTO(dep);
            return ResponseEntity.ok(depDTO);
        } catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Integer id) {
        try {
            departmentService.deleteDepartment(id);
            return ResponseEntity.ok("Department deleted");
        } catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Integer id) {
        try {
            DepartmentDTO departmentDTO = DepartmentMapper.INSTANCE.departmentToDepartmentDTO(departmentService.getDepartment(id));
            return ResponseEntity.ok(departmentDTO);
        } catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }




}
