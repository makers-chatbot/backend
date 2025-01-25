package icesi.cmr.services.impl;

import icesi.cmr.dto.DeliveryCertificateDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.exceptions.NotEnoughStockException;
import icesi.cmr.mappers.DeliveryCertificateMapper;
import icesi.cmr.model.noRelational.products.Product;
import icesi.cmr.model.relational.equipments.DeliveryCertificate;
import icesi.cmr.model.relational.equipments.Equipment;
import icesi.cmr.repositories.equipments.ContractRepository;
import icesi.cmr.repositories.equipments.DeliveryCertificateRepository;
import icesi.cmr.repositories.equipments.EquipmentRepository;
import icesi.cmr.services.interfaces.DeliveryCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeliveryCertificateServiceImpl implements DeliveryCertificateService {


    @Autowired
    private DeliveryCertificateRepository deliveryCertificateRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void generateDeliveryCertificate(DeliveryCertificateDTO deliveryCertificateDTO) {

        System.out.println("DeliveryCertificateDTO on serviice:" + deliveryCertificateDTO);

        DeliveryCertificate deliveryCertificate = DeliveryCertificateMapper.INSTANCE.toEntity(deliveryCertificateDTO);

        System.out.println("DeliveryCertificate on service:" + deliveryCertificate);

        deliveryCertificate.setContract(contractRepository.findById(deliveryCertificateDTO.getContractId()).orElseThrow(() -> new EntityNotFound("Contract not found")));

        Equipment equipment = equipmentRepository.findByInventaryCode(deliveryCertificateDTO.getEquipmentId());

        if (equipment == null) {
            throw new EntityNotFound("Equipment not found");
        }

        if (equipment.getStock() < deliveryCertificate.getQuantity()){

            throw new NotEnoughStockException("Not enough stock");
        }

        equipment.setStock(equipment.getStock() - deliveryCertificate.getQuantity());




        Query query = new Query(Criteria.where("_id").is(equipment.getInventaryCode()));

        Update update = new Update();

        update.set("stock", equipment.getStock());


        deliveryCertificate.setEquipment(equipment);


        mongoTemplate.updateFirst(query, update, Product.class);

        equipmentRepository.save(equipment);

        deliveryCertificateRepository.save(deliveryCertificate);

    }

    @Override
    public void updateDeliveryCertificate( DeliveryCertificateDTO deliveryCertificateDTO, Integer id) {


        DeliveryCertificate existingDeliveryCertificate = deliveryCertificateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("DeliveryCertificate not found"));
        DeliveryCertificateMapper.INSTANCE.updateDeliveryCertificateFromDto(deliveryCertificateDTO, existingDeliveryCertificate);


        if (deliveryCertificateDTO.getEquipmentId() == null) {

            deliveryCertificateRepository.save(existingDeliveryCertificate);
            return;
        }

        Equipment equipment = equipmentRepository.findByInventaryCode(deliveryCertificateDTO.getEquipmentId());

        if (equipment == null) {
            throw new EntityNotFound("Equipment not found");
        }

        if (equipment.getStock() < deliveryCertificateDTO.getQuantity()){

            throw new NotEnoughStockException("Not enough stock");
        }

        equipment.setStock(equipment.getStock() - deliveryCertificateDTO.getQuantity());

        Query query = new Query(Criteria.where("_id").is(equipment.getInventaryCode()));

        Update update = new Update();

        update.set("stock", equipment.getStock());

        existingDeliveryCertificate.setEquipment(equipment);

        mongoTemplate.updateFirst(query, update, Product.class);

        equipmentRepository.save(equipment);

        deliveryCertificateRepository.save(existingDeliveryCertificate);

    }

    @Override
    public void deleteDeliveryCertificate(Integer id) {
        deliveryCertificateRepository.deleteById(id);
    }

    @Override
    public List<DeliveryCertificate> getAllDeliveryCertificates() {
        return deliveryCertificateRepository.findAll();
    }

    @Override
    public List<DeliveryCertificate> getDeliveryCertificateByContract(Integer departmentId) {
        return deliveryCertificateRepository.findByContractId(departmentId);
    }


}
