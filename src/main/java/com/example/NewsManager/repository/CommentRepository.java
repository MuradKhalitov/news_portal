package com.example.NewsManager.repository;
import com.example.NewsManager.model.Category;
import com.example.NewsManager.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByNewsId(Long newsId);
    Page<Comment> findByNewsId(Long newsId, Pageable pageable);
//    @Query("SELECT c FROM Category c WHERE c.name = ?1")
//    Page<Category> findAllByCategory(String name, Pageable pageable);
}

