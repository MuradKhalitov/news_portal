package com.example.NewsManager.dto.response;

import com.example.NewsManager.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BriefNewsDTO {
    private Long id;
    private String title;
    private String content;
    private Long author;
    private Long category;
    private Integer comments;

    public void setUsername(String username) {
    }
}
