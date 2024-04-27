package com.example.NewsManager.repository;
import com.example.NewsManager.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    // Здесь можно объявить дополнительные методы для работы с новостями, если это необходимо
}

