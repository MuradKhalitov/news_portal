package com.example.NewsManager.service;

import com.example.NewsManager.exception.CommentNotFoundException;
import com.example.NewsManager.model.Comment;
import com.example.NewsManager.model.User;
import com.example.NewsManager.repository.CommentRepository;
import com.example.NewsManager.repository.UserRepository;
import com.example.NewsManager.util.CurrentUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(Comment comment) {
        String currentUsername = CurrentUsers.getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername).get();
        comment.setAuthor(user);
        log.info("Пользователь: {}, добавил новость", currentUsername);
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
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

