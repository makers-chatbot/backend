package icesi.cmr.repositories.equipments;

import icesi.cmr.model.relational.equipments.DeliveryCertificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryCertificateRepository extends JpaRepository<DeliveryCertificate, Integer> {

    List<DeliveryCertificate> findByContractId(Integer contractId);

}
