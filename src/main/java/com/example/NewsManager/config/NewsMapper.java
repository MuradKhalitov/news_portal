package com.example.NewsManager.config;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.example.NewsManager.model.News;
import com.example.NewsManager.dto.NewsDTO;

@Mapper
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mapping(source = "author.username", target = "authorUsername")
    NewsDTO newsToNewsDTO(News news);

    @Mapping(source = "authorUsername", target = "author.username")
    News newsDTOToNews(NewsDTO newsDTO);
}
