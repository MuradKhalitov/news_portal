package com.example.NewsManager.aop;

import com.example.NewsManager.model.Comment;
import com.example.NewsManager.model.News;
import com.example.NewsManager.service.CommentService;
import com.example.NewsManager.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Component
@Slf4j
public class AutentificatorAspect {
    private final NewsService newsService;
    private final CommentService commentService;

    public AutentificatorAspect(NewsService service, CommentService commentService) {
        this.newsService = service;
        this.commentService = commentService;
    }

    @Around("@annotation(com.example.NewsManager.aop.Autentificator)")
    public Object authAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Long oldNewsId = null;
        Long oldCommentId = null;
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if ((methodSignature.getName().equals("updateNews")) || (methodSignature.getName().equals("deleteNews"))) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof Long) {
                    oldNewsId = (Long) arg;
                }
            }
            if (oldNewsId != null) {
                String currentUsername = getCurrentUsername();
                log.info("Пользователь: {}, делает попытку отредактировать или удалить новость", currentUsername);
                News news = newsService.getNewsById(oldNewsId);
                if (news.getAuthor().getUsername().equals(currentUsername)) {
                    log.info("У пользователя: {}, все получилось!", currentUsername);
                } else {
                    log.info("У {}, недостаточно прав для редактирования или удаления!", currentUsername);
                    return null;
                }
            }
        } else if ((methodSignature.getName().equals("updateComment")) || (methodSignature.getName().equals("deleteComment"))) {
            Object[] arguments = joinPoint.getArgs();
            for (Object arg : arguments) {
                if (arg instanceof Long) {
                    oldCommentId = (Long) arg;
                }
            }
            if (oldCommentId != null) {
                String currentUsername = getCurrentUsername();
                log.info("Пользователь: {}, делает попытку отредактировать или удалить комментарий", currentUsername);
                Comment comment = commentService.getCommentById(oldCommentId);
                if (comment.getAuthor().getUsername().equals(currentUsername)) {
                    log.info("У пользователя: {}, все получилось!", currentUsername);
                } else {
                    log.info("У {}, недостаточно прав для редактирования или удаления!", currentUsername);
                    return null;
                }
            }
        }
        Object result = joinPoint.proceed();
        return result;
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
}

