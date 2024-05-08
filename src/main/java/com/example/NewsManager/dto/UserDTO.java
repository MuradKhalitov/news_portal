package com.example.NewsManager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class UserDTO {
    private Long id;
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;
    private String email;
    private String password;
}

