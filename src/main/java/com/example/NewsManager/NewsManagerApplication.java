package com.example.NewsManager;

import com.example.NewsManager.model.User;
import com.example.NewsManager.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NewsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsManagerApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.createUser(new User(null, "Murad", "darggun@gmail,com", "111"));
//            userService.createUser(new User(null, "Roma", "roma@gmail,com", "111"));
//            userService.createUser(new User(null, "Rusik", "rusik@gmail,com", "111"));
//        };
//    }

}
