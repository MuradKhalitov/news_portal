package com.example.NewsManager.repository;
import com.example.NewsManager.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name = ?1")
    Page<Category> findAllByCategory(String name, Pageable pageable);
}
