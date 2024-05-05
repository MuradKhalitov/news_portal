package com.example.NewsManager.repository;
import com.example.NewsManager.model.Category;
import com.example.NewsManager.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    @Query("SELECT n FROM News n WHERE n.content = ?1")
    Page<News> findAllByNews(String content, Pageable pageable);
}

