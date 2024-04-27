package com.example.NewsManager.controller;

import com.example.NewsManager.dto.NewsDTO;
import com.example.NewsManager.model.News;
import com.example.NewsManager.service.NewsService;
import com.example.NewsManager.utils.DTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;
    private final DTOMapper dtoMapper;

    @Autowired
    public NewsController(NewsService newsService, DTOMapper dtoMapper) {
        this.newsService = newsService;
        this.dtoMapper = dtoMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO) {
        News news = dtoMapper.convertToEntity(newsDTO);
        News createdNews = newsService.createNews(news);
        NewsDTO createdNewsDTO = dtoMapper.convertToDTO(createdNews);
        return new ResponseEntity<>(createdNewsDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<News> newsList = newsService.getAllNews();
        List<NewsDTO> newsDTOList = newsList.stream()
                .map(dtoMapper::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(newsDTOList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id);
        NewsDTO newsDTO = dtoMapper.convertToDTO(news);
        return new ResponseEntity<>(newsDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id, @RequestBody NewsDTO newsDTO) {
        News news = dtoMapper.convertToEntity(newsDTO);
        News updatedNews = newsService.updateNews(id, news);
        NewsDTO updatedNewsDTO = dtoMapper.convertToDTO(updatedNews);
        return new ResponseEntity<>(updatedNewsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


