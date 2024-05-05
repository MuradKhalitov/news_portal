package com.example.NewsManager.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryDTO {
    private Long id;
    @NotNull
    @Size(min = 3, max = 20, message = "Min size for category: {min}. Max size is: {max}")
    private String name;
}
