package com.example.NewsManager.dto;

import com.example.NewsManager.model.News;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String content;
    private Long author;
    private Long news;
}
