package icesi.cmr.repositories.equipments;

import icesi.cmr.model.relational.equipments.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Integer> {

    EquipmentCategory findByName(String name);


}
