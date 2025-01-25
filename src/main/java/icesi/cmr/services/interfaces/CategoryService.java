package icesi.cmr.services.interfaces;

import icesi.cmr.dto.CategoryDTO;
import icesi.cmr.model.relational.equipments.EquipmentCategory;

import java.util.List;

public interface CategoryService {

    void saveCategory(CategoryDTO categoryDTO);

    void deleteCategory(Integer id);

    void updateCategory(CategoryDTO categoryDTO);

    EquipmentCategory getCategory(String name);

    List<EquipmentCategory> getCategories();


}
