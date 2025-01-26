package by.kapinskiy.p2w.services;

import by.kapinskiy.p2w.models.Category;
import by.kapinskiy.p2w.models.Lot;
import by.kapinskiy.p2w.repositories.CategoriesRepository;
import by.kapinskiy.p2w.repositories.LotsRepository;
import by.kapinskiy.p2w.util.exceptions.EntityNotCreatedException;
import by.kapinskiy.p2w.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final LotsRepository lotsRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository, LotsRepository lotsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.lotsRepository = lotsRepository;
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Category createCategory(Category category, Optional<Integer> parentCategoryId) {
        if (parentCategoryId.isPresent()) {
            Category parent = categoriesRepository.findById(parentCategoryId.get()).orElseThrow(() -> new NotFoundException("Parent category not found"));
            if (parent.getSubCategories().stream().anyMatch(c -> c.getName().equalsIgnoreCase(category.getName()))) {
                throw new EntityNotCreatedException("Category already exists");
            }
            category.setParentCategory(parent);
            parent.getSubCategories().add(category);
        } else {
            if (categoriesRepository.findByNameAndParentCategoryIsNull(category.getName()).isPresent()) {
                throw new EntityNotCreatedException("Category already exists");
            }
        }

        return categoriesRepository.save(category);
    }

    public List<Category> findMainCategories() {
        return categoriesRepository.findByParentCategoryIsNull();
    }

    public List<Category> findByName(String name) {
        return categoriesRepository.findByNameContainsIgnoreCase(name);
    }

    public List<Category> findSubCategories(int id) {
        return categoriesRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found")).getSubCategories();
    }

    public List<Lot> findAllLots(int categoryId){
        if (categoriesRepository.existsById(categoryId)) {
            return lotsRepository.findAllByCategory_Id(categoryId);
        }
        throw new NotFoundException("Category not found");
    }

    public Category getParentCategory(int categoryId) {
        return categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id " + categoryId + " not found"))
                .getParentCategory();
    }

}
