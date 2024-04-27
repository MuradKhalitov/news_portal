package com.example.NewsManager.service;
import com.example.NewsManager.exception.NewsNotFoundException;
import com.example.NewsManager.model.News;
import com.example.NewsManager.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public News createNews(News news) {
        return newsRepository.save(news);
    }

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException("News with id " + id + " not found"));
    }

    public News updateNews(Long id, News updatedNews) {
        News news = getNewsById(id);
        news.setTitle(updatedNews.getTitle());
        news.setContent(updatedNews.getContent());
        news.setAuthor(updatedNews.getAuthor());
        news.setCategory(updatedNews.getCategory());
        return newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}

