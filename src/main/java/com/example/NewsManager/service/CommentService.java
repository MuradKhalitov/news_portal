package com.example.NewsManager.service;
import com.example.NewsManager.exception.CommentNotFoundException;
import com.example.NewsManager.model.Category;
import com.example.NewsManager.model.Comment;
import com.example.NewsManager.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByNewsId(Long newsId, PageRequest pageRequest) {
        Page<Comment> page = commentRepository.findAll(pageRequest);
        return page.getContent();
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment with id " + id + " not found"));
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        Comment comment = getCommentById(id);
        comment.setContent(updatedComment.getContent());
        // Если требуется обновление других полей комментария, добавьте их обновление здесь
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

