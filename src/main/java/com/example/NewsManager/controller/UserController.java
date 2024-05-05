package com.example.NewsManager.controller;

import com.example.NewsManager.dto.UserDTO;
import com.example.NewsManager.mapper.UserMapper;
import com.example.NewsManager.model.User;
import com.example.NewsManager.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }
    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        User user = userMapper.convertToEntity(userDTO);
        User createdUser = userService.createUser(user);
        UserDTO createdUserDTO = userMapper.convertToDTO(createdUser);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "2") int size) {
        List<User> userList = userService.getAllUsers(PageRequest.of(page, size));
        List<UserDTO> userDTOList = userList.stream()
                .map(userMapper::convertToDTO)
                .collect(Collectors.toList());
        return userDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDTO userDTO = userMapper.convertToDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        User user = userMapper.convertToEntity(userDTO);
        User updatedUser = userService.updateUser(id, user);
        UserDTO updatedUserDTO = userMapper.convertToDTO(updatedUser);
        return new ResponseEntity<>(updatedUserDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


