package com.example.NewsManager.controller;

import com.example.NewsManager.dto.CommentDTO;
import com.example.NewsManager.mapper.CommentMapper;
import com.example.NewsManager.model.Comment;
import com.example.NewsManager.model.User;
import com.example.NewsManager.service.CommentService;
import com.example.NewsManager.service.UserService;
import com.example.NewsManager.util.CurrentUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final CommentMapper commentMapper;

    @Autowired
    public CommentController(CommentService commentService, UserService userService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.commentMapper = commentMapper;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentMapper.convertToEntity(commentDTO);
        Comment createdComment = commentService.createComment(comment);
        CommentDTO createdCommentDTO = commentMapper.convertToDTO(createdComment);
        return new ResponseEntity<>(createdCommentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/news/{newsId}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public List<CommentDTO> getCommentsByNewsId(@PathVariable Long newsId,
                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                @RequestParam(required = false, defaultValue = "2") int size) {
        List<Comment> comments = commentService.getCommentsByNewsId(newsId, PageRequest.of(page, size));
        List<CommentDTO> commentDTOs = comments.stream()
                .map(commentMapper::convertToDTO)
                .collect(Collectors.toList());
        return commentDTOs;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        CommentDTO commentDTO = commentMapper.convertToDTO(comment);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        String currentUsername = CurrentUsers.getCurrentUsername();
        User currentUser = userService.findByUsername(currentUsername);

        Comment oldComment = commentService.getCommentById(id);
        User authorComment = oldComment.getAuthor();

        if (currentUser.getId().equals(authorComment.getId())) {
            Comment comment = commentMapper.convertToEntity(commentDTO);
            Comment updatedComment = commentService.updateComment(id, comment);
            CommentDTO updatedCommentDTO = commentMapper.convertToDTO(updatedComment);
            return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        String currentUsername = CurrentUsers.getCurrentUsername();
        User currentUser = userService.findByUsername(currentUsername);
        Comment deletedComment = commentService.getCommentById(id);
        User authorComment = deletedComment.getAuthor();

        if (currentUser.getId().equals(authorComment.getId()) || CurrentUsers.hasRole("ADMIN") || CurrentUsers.hasRole("MODERATOR")) {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

