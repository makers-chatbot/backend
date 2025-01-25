package icesi.cmr.services.interfaces;


import icesi.cmr.dto.DeliveryCertificateDTO;
import icesi.cmr.model.relational.equipments.DeliveryCertificate;

import java.util.List;

public interface DeliveryCertificateService {


    void generateDeliveryCertificate(DeliveryCertificateDTO deliveryCertificateDTO);

    void updateDeliveryCertificate(DeliveryCertificateDTO deliveryCertificateDTO, Integer id);

    void deleteDeliveryCertificate(Integer id);

    List<DeliveryCertificate> getAllDeliveryCertificates();

    List<DeliveryCertificate> getDeliveryCertificateByContract(Integer contractId);


}
