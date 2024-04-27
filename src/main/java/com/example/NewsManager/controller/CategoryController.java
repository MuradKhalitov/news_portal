package com.example.NewsManager.controller;
import com.example.NewsManager.dto.CategoryDTO;
import com.example.NewsManager.model.Category;
import com.example.NewsManager.service.CategoryService;
import com.example.NewsManager.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final DTOMapper dtoMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, DTOMapper dtoMapper) {
        this.categoryService = categoryService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = dtoMapper.convertToEntity(categoryDTO);
        Category createdCategory = categoryService.createCategory(category);
        CategoryDTO createdCategoryDTO = dtoMapper.convertToDTO(createdCategory);
        return new ResponseEntity<>(createdCategoryDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTO = dtoMapper.convertToDTO(category);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = dtoMapper.convertToEntity(categoryDTO);
        Category updatedCategory = categoryService.updateCategory(id, category);
        CategoryDTO updatedCategoryDTO = dtoMapper.convertToDTO(updatedCategory);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


