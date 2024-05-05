package com.example.NewsManager.mapper;
import com.example.NewsManager.dto.UserDTO;
import com.example.NewsManager.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User convertToEntity(UserDTO userDTO);

    UserDTO convertToDTO(User user);
}
