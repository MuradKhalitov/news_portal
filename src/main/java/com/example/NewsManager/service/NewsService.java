package com.example.NewsManager.service;

import com.example.NewsManager.aop.Autentificator;

import com.example.NewsManager.exception.NewsNotFoundException;
import com.example.NewsManager.model.News;
import com.example.NewsManager.model.User;
import com.example.NewsManager.repository.NewsRepository;
import com.example.NewsManager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    public News createNews(News news) {
        String currentUsername = getCurrentUsername();
        User user = userRepository.findByUsername(currentUsername).get();
        news.setAuthor(user);
        log.info("Пользователь: {}, добавил новость", currentUsername);
        return newsRepository.save(news);
    }

    public List<News> getAllNews(PageRequest pageRequest) {
        Page<News> page = newsRepository.findAll(pageRequest);
        return page.getContent();
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News with id " + id + " not found"));
    }

    @Autentificator
    public News updateNews(Long id, News updatedNews) {
        News news = getNewsById(id);
        news.setTitle(updatedNews.getTitle());
        news.setContent(updatedNews.getContent());
        news.setCategory(updatedNews.getCategory());
        return newsRepository.save(news);
    }

    @Autentificator
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername();
            } else {
                // Вернуть имя пользователя напрямую, если UserDetails не используется
                return principal.toString();
            }
        }
        return null;
    }

    public List<News> getFilteredNewsByAuthor(Long authorIds) {
        if (!(authorIds == null)) {
            return newsRepository.findByAuthorId(authorIds);
        }
        return new ArrayList<>();
    }

    public List<News> getFilteredNewsByCategory(Long categoryIds) {
        if (!(categoryIds == null)) {
            return newsRepository.findByCategoryId(categoryIds);
        }
        return new ArrayList<>();
    }
}

