package icesi.cmr.controllers;

import icesi.cmr.dto.InteractionRequestDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.model.noRelational.CRM.CustomerRelationshipManagement;
import icesi.cmr.services.interfaces.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;


    @PostMapping
    public ResponseEntity<String> addInteraction(@RequestBody InteractionRequestDTO interactionRequestDTO) {

        try {

            interactionService.createInteaction(interactionRequestDTO);
            return ResponseEntity.ok("Interaction created");

        } catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {

            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }


    @GetMapping
    public ResponseEntity<CustomerRelationshipManagement> getInteractions(

            @RequestParam String companyId,
            @RequestParam String departmentId

    ) {
        try {

            return ResponseEntity
                    .ok(interactionService.getInteractionsByCompanyAndDepartment(companyId, departmentId)
                            .orElseThrow(() -> new EntityNotFound("Interaction not found")));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
