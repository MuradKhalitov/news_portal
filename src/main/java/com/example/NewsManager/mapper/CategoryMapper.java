package com.example.NewsManager.mapper;

import com.example.NewsManager.dto.CategoryDTO;
import com.example.NewsManager.model.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category convertToEntity(CategoryDTO categoryDTO);

    CategoryDTO convertToDTO(Category category);

}
