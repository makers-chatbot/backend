package icesi.cmr.repositories.equipments;

import icesi.cmr.model.relational.equipments.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    Equipment findByInventaryCode(String inventaryCode);


}
