package com.example.NewsManager.dto;

import com.example.NewsManager.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private Long author;
    private Long category;
    private List<CommentDTO> comments = new ArrayList<>();
}
