package icesi.cmr.services.impl;

import icesi.cmr.dto.CategoryDTO;
import icesi.cmr.exceptions.AlreadyExistEntity;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.mappers.CategoryMapper;
import icesi.cmr.model.relational.equipments.EquipmentCategory;
import icesi.cmr.repositories.equipments.EquipmentCategoryRepository;
import icesi.cmr.repositories.equipments.EquipmentRepository;
import icesi.cmr.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void saveCategory(CategoryDTO categoryDTO) {


        System.out.println("Category to save on category service: " + categoryDTO);

        EquipmentCategory category = categoryMapper.toEntity(categoryDTO);

        if (equipmentCategoryRepository.findByName(category.getName()) != null) {
            throw new AlreadyExistEntity("Category already exist");
        }

        equipmentCategoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Integer id) {


        EquipmentCategory category = equipmentCategoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new EntityNotFound("Category not found");
        }
        equipmentCategoryRepository.deleteById(id);

    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {

    }

    @Override
    public EquipmentCategory getCategory(String name) {

        return equipmentCategoryRepository.findByName(name);

    }

    @Override
    public List<EquipmentCategory> getCategories() {
        return equipmentCategoryRepository.findAll();

    }
}
