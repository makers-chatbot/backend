package icesi.cmr.controllers;


import icesi.cmr.dto.DeliveryCertificateDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.exceptions.NotEnoughStockException;
import icesi.cmr.mappers.DeliveryCertificateMapper;
import icesi.cmr.services.interfaces.DeliveryCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deliveryCertificates")
public class DeliveryCertificateController {

    @Autowired
    private DeliveryCertificateService deliveryCertificateService;


    @PostMapping
    public ResponseEntity<String> createDeliveryCertificate(@RequestBody DeliveryCertificateDTO deliveryCertificateDTO) {
        {

            try {
                deliveryCertificateService.generateDeliveryCertificate(deliveryCertificateDTO);
                return ResponseEntity.ok("Delivery certificate created");
            } catch (EntityNotFound e) {

                e.printStackTrace();
                return ResponseEntity.notFound().build();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping()
    public ResponseEntity<List<DeliveryCertificateDTO>> getDeliveryCertificates() {
        try {

            List<DeliveryCertificateDTO> deliveryCertificateDTOS =
                    deliveryCertificateService.getAllDeliveryCertificates()
                            .stream()
                            .map(DeliveryCertificateMapper.INSTANCE::toDTO)
                            .toList();
            return ResponseEntity.ok(deliveryCertificateDTOS);
        }catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{contractId}")
    public ResponseEntity<List<DeliveryCertificateDTO>> getDeliveryCertificatesByContract(@PathVariable Integer contractId) {
        try {
            List<DeliveryCertificateDTO> deliveryCertificateDTOS =
                    deliveryCertificateService.getDeliveryCertificateByContract(contractId)
                            .stream()
                            .map(DeliveryCertificateMapper.INSTANCE::toDTO)
                            .toList();
            return ResponseEntity.ok(deliveryCertificateDTOS);
        }catch (EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateDeliveryCertificate(@RequestBody DeliveryCertificateDTO deliveryCertificateDTO, @PathVariable Integer id) {
        try {
            deliveryCertificateService.updateDeliveryCertificate(deliveryCertificateDTO, id);
            return ResponseEntity.ok("Delivery certificate updated");
        } catch (NotEnoughStockException | EntityNotFound e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }





}
