package com.example.NewsManager.controller;
import com.example.NewsManager.dto.CategoryDTO;
import com.example.NewsManager.mapper.CategoryMapper;
import com.example.NewsManager.model.Category;
import com.example.NewsManager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODERATOR')")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category createdCategory = categoryService.createCategory(category);
        CategoryDTO createdCategoryDTO = categoryMapper.convertToDTO(createdCategory);
        return new ResponseEntity<>(createdCategoryDTO, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public List<CategoryDTO> getAllCategories(@RequestParam(required = false, defaultValue = "0") int page,
                                              @RequestParam(required = false, defaultValue = "2") int size) {
        List<Category> categories = categoryService.getAllCategories(PageRequest.of(page, size));
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::convertToDTO)
                .collect(Collectors.toList());
        return categoryDTOs;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        CategoryDTO categoryDTO = categoryMapper.convertToDTO(category);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODERATOR')")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category updatedCategory = categoryService.updateCategory(id, category);
        CategoryDTO updatedCategoryDTO = categoryMapper.convertToDTO(updatedCategory);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('MODERATOR')")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


