package com.example.NewsManager.mapper;

import com.example.NewsManager.dto.response.BriefNewsDTO;
import com.example.NewsManager.model.News;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NewsMapperDelegate implements NewsMapper {

    @Autowired
    private NewsMapper delegate;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public BriefNewsDTO convertToBriefDTO(News news) {
        BriefNewsDTO briefNewsDTO = delegate.convertToBriefDTO(news);
        briefNewsDTO.setUsername(news.getAuthor().getUsername());
        briefNewsDTO.setComments(news.getComments().size());

        return briefNewsDTO;
    }
}

