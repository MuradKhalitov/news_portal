package com.example.NewsManager.controller;

import com.example.NewsManager.aop.Autorizator;
import com.example.NewsManager.dto.NewsDTO;
import com.example.NewsManager.dto.response.BriefNewsDTO;
import com.example.NewsManager.mapper.NewsMapper;
import com.example.NewsManager.model.News;
import com.example.NewsManager.model.User;
import com.example.NewsManager.service.NewsService;
import com.example.NewsManager.service.UserService;
import com.example.NewsManager.util.CurrentUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;
    private final NewsMapper newsMapper;

    @Autowired
    public NewsController(NewsService newsService, UserService userService, NewsMapper newsMapper) {
        this.newsService = newsService;
        this.userService = userService;
        this.newsMapper = newsMapper;
    }

    @GetMapping("/filter_by_author")
    public List<BriefNewsDTO> getFilteredNewsByAuthor(@RequestParam(required = false) Long authorIds) {
        List<News> filterNewsList = newsService.getFilteredNewsByAuthor(authorIds);
        List<BriefNewsDTO> briefNewsDTOList = filterNewsList.stream()
                .map(newsMapper::convertToBriefDTO)
                .collect(Collectors.toList());
        return briefNewsDTOList;
    }

    @GetMapping("/filter_by_category")
    public List<BriefNewsDTO> getFilteredNewsByCategory(@RequestParam(required = false) Long categoryIds) {
        List<News> filterNewsList = newsService.getFilteredNewsByCategory(categoryIds);
        List<BriefNewsDTO> briefNewsDTOList = filterNewsList.stream()
                .map(newsMapper::convertToBriefDTO)
                .collect(Collectors.toList());
        return briefNewsDTOList;
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO) {
        News news = newsMapper.convertToEntity(newsDTO);
        News createdNews = newsService.createNews(news);
        NewsDTO createdNewsDTO = newsMapper.convertToDTO(createdNews);
        return new ResponseEntity<>(createdNewsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public List<BriefNewsDTO> getAllNews(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "2") int size) {
        List<News> newsList = newsService.getAllNews(PageRequest.of(page, size));
        List<BriefNewsDTO> briefNewsDTOList = newsList.stream()
                .map(newsMapper::convertToBriefDTO)
                .collect(Collectors.toList());
        return briefNewsDTOList;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') || hasAuthority('USER') || hasAuthority('MODERATOR')")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id);
        NewsDTO newsDTO = newsMapper.convertToDTO(news);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id, @RequestBody NewsDTO newsDTO) {
        String currentUsername = CurrentUsers.getCurrentUsername();
        User currentUser = userService.findByUsername(currentUsername);

        News oldNews = newsService.getNewsById(id);
        User authorNews = oldNews.getAuthor();

        if (currentUser.getId().equals(authorNews.getId())) {
            News news = newsMapper.convertToEntity(newsDTO);
            News updatedNews = newsService.updateNews(id, news);
            NewsDTO updatedNewsDTO = newsMapper.convertToDTO(updatedNews);
            return new ResponseEntity<>(updatedNewsDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/{id}")
    @Autorizator
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        String currentUsername = CurrentUsers.getCurrentUsername();
        User currentUser = userService.findByUsername(currentUsername);
        News deletedNews = newsService.getNewsById(id);
        User authorNews = deletedNews.getAuthor();

        if (currentUser.getId().equals(authorNews.getId()) || CurrentUsers.hasRole("ADMIN") || CurrentUsers.hasRole("MODERATOR")) {
            newsService.deleteNews(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}


