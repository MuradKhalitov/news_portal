package com.example.NewsManager.utils;

import com.example.NewsManager.config.NewsMapper;
import com.example.NewsManager.dto.CategoryDTO;
import com.example.NewsManager.dto.CommentDTO;
import com.example.NewsManager.dto.NewsDTO;
import com.example.NewsManager.dto.UserDTO;
import com.example.NewsManager.exception.CategoryNotFoundException;
import com.example.NewsManager.model.Category;
import com.example.NewsManager.model.Comment;
import com.example.NewsManager.model.News;
import com.example.NewsManager.model.User;
import com.example.NewsManager.repository.CategoryRepository;
import com.example.NewsManager.repository.NewsRepository;
import com.example.NewsManager.repository.UserRepository;
import com.example.NewsManager.service.CategoryService;
import com.example.NewsManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DTOMapper {

    private final UserService userService;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    private final NewsMapper newsMapper;

    @Autowired
    public DTOMapper(UserService userService, CategoryService categoryService, UserRepository userRepository, NewsRepository newsRepository, CategoryRepository categoryRepository, NewsMapper newsMapper) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
        this.categoryRepository = categoryRepository;
        this.newsMapper = newsMapper;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        return userDTO;
    }

    public CategoryDTO convertToDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        return categoryDTO;
    }

    public NewsDTO convertToDTO(News news) {
//        NewsDTO newsDTO = new NewsDTO();
//        newsDTO.setId(news.getId());
//        newsDTO.setTitle(news.getTitle());
//        newsDTO.setContent(news.getContent());
//        newsDTO.setAuthor(news.getAuthor().getId());
//        newsDTO.setCategory(news.getId());
//        List<CommentDTO> commentDTOList = news.getComments().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//        newsDTO.setComments(commentDTOList);
//        return newsDTO;
        return newsMapper.newsToNewsDTO(news);
    }

    public CommentDTO convertToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setContent(comment.getContent());
        commentDTO.setAuthor(comment.getAuthor().getId());
        commentDTO.setNews(comment.getNews().getId());
        return commentDTO;
    }

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    public News convertToEntity(NewsDTO newsDTO) throws CategoryNotFoundException {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setAuthor(userRepository.getById(newsDTO.getAuthor()));
        news.setCategory(categoryRepository.getById(newsDTO.getCategory()));
        List<Comment> comments = newsDTO.getComments().stream()
                .map(commentDTO -> {
                    Comment comment = new Comment();
                    comment.setContent(commentDTO.getContent());
                    comment.setAuthor(userService.getUserById(commentDTO.getAuthor()));
                    comment.setNews(news);
                    return comment;
                })
                .collect(Collectors.toList());

        // Установка списка комментариев для новости
        news.setComments(comments);
        return news;
    }
    public Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        return category;
    }
    public Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setAuthor(userService.getUserById(commentDTO.getAuthor()));
        comment.setNews(newsRepository.getById(commentDTO.getNews()));
        return comment;
    }


    // Методы для преобразования CategoryDTO, NewsDTO и CommentDTO в сущности необходимо определить по аналогии
}
