package icesi.cmr.controllers;

import icesi.cmr.dto.CategoryDTO;
import icesi.cmr.exceptions.AlreadyExistEntity;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.services.interfaces.CategoryService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {

        try {

            categoryService.saveCategory(categoryDTO);
            return ResponseEntity.ok().body("Category created successfully");

        }catch (AlreadyExistEntity e){

            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());

        }catch (Exception e){

            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());

        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().body("Category deleted successfully");
        } catch (EntityNotFound e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getCategories() {
        try {
            return ResponseEntity.ok().body(categoryService.getCategories());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }




}
