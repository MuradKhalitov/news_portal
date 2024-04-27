package com.example.NewsManager.controller;
import com.example.NewsManager.dto.CommentDTO;
import com.example.NewsManager.model.Comment;
import com.example.NewsManager.service.CommentService;
import com.example.NewsManager.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final DTOMapper dtoMapper;

    @Autowired
    public CommentController(CommentService commentService, DTOMapper dtoMapper) {
        this.commentService = commentService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = dtoMapper.convertToEntity(commentDTO);
        Comment createdComment = commentService.createComment(comment);
        CommentDTO createdCommentDTO = dtoMapper.convertToDTO(createdComment);
        return new ResponseEntity<>(createdCommentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByNewsId(@PathVariable Long newsId) {
        List<Comment> comments = commentService.getCommentsByNewsId(newsId);
        List<CommentDTO> commentDTOs = comments.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        CommentDTO commentDTO = dtoMapper.convertToDTO(comment);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Comment comment = dtoMapper.convertToEntity(commentDTO);
        Comment updatedComment = commentService.updateComment(id, comment);
        CommentDTO updatedCommentDTO = dtoMapper.convertToDTO(updatedComment);
        return new ResponseEntity<>(updatedCommentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

