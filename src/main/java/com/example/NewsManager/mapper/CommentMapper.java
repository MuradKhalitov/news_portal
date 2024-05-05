package com.example.NewsManager.mapper;
import com.example.NewsManager.dto.CommentDTO;
import com.example.NewsManager.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, NewsMapper.class})
public interface CommentMapper {

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "news.id", target = "news")
    CommentDTO convertToDTO(Comment comment);

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "news", target = "news.id")
    Comment convertToEntity(CommentDTO commentDTO);
}
