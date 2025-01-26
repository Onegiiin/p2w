package by.kapinskiy.p2w.controllers;

import by.kapinskiy.p2w.DTO.CategoryDTO;
import by.kapinskiy.p2w.DTO.CreateCategoryDTO;
import by.kapinskiy.p2w.DTO.LotDTO;
import by.kapinskiy.p2w.models.Category;
import by.kapinskiy.p2w.services.CategoriesService;
import by.kapinskiy.p2w.util.MappingUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;
    private final MappingUtil mappingUtil;

    @Autowired
    public CategoriesController(CategoriesService categoriesService, MappingUtil mappingUtil) {
        this.categoriesService = categoriesService;
        this.mappingUtil = mappingUtil;
    }

    @GetMapping
    public List<CategoryDTO> getMainCategories() {
        return mappingUtil.categoriesListToDTO(categoriesService.findMainCategories());
    }

    @GetMapping("/{id}/subcategories")
    public List<CategoryDTO> getSubCategories(@PathVariable int id) {
        return mappingUtil.categoriesListToDTO(categoriesService.findSubCategories(id));
    }

    @GetMapping("/search")
    public List<CategoryDTO> findCategories(@RequestParam(name = "name")String name) {
        return mappingUtil.categoriesListToDTO(categoriesService.findByName(name));
    }

    @GetMapping("/{id}/lots")
    public List<LotDTO> getLots(@PathVariable int id) {
        return mappingUtil.lotsListToDTO(categoriesService.findAllLots(id));
    }

    @GetMapping("/{id}/parent-category")
    public CategoryDTO getParentCategory(@PathVariable int id) {
        return mappingUtil.categoryToDTO(categoriesService.getParentCategory(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody @Valid CreateCategoryDTO createCategoryDTO) {
        Category createdCategory = categoriesService.createCategory(mappingUtil.createCategoryDTOToCategory(createCategoryDTO), Optional.ofNullable(createCategoryDTO.getParentCategoryId()));
        return new ResponseEntity<>(mappingUtil.categoryToDTO(createdCategory), HttpStatus.CREATED);
    }


}
