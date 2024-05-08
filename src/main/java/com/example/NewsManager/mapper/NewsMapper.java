package com.example.NewsManager.mapper;
import com.example.NewsManager.dto.NewsDTO;
import com.example.NewsManager.dto.response.BriefNewsDTO;
import com.example.NewsManager.model.Comment;
import com.example.NewsManager.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CategoryMapper.class, CommentMapper.class})
public interface NewsMapper {

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "comments", target = "comments")
    NewsDTO convertToDTO(News news);
    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "category.id", target = "category")
    @Mapping(source = "comments", target = "comments")
    BriefNewsDTO convertToBriefDTO(News news);

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "category", target = "category.id")
    @Mapping(source = "comments", target = "comments")
    News convertToEntity(NewsDTO newsDTO);
    default Integer mapComments(List<Comment> comments) {
        return comments.size();
    }
}
