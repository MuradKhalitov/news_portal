package com.example.NewsManager.repository;
import com.example.NewsManager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
    // Другие методы репозитория, если необходимо
}
