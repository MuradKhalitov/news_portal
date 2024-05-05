package com.example.NewsManager.service;
import com.example.NewsManager.exception.UserNotFoundException;
import com.example.NewsManager.model.User;
import com.example.NewsManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository){//, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        //this.passwordEncoder = passwordEncoder;
    }

//    public void registerUser(User user) {
//        // Проверяем, существует ли пользователь с таким именем
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new RuntimeException("Такой пользователь уже существует!");
//        }
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//    }
    public User createUser(User user) {
        // Проверяем, существует ли пользователь с таким именем
//        if (userRepository.existsByUsername(user.getUsername())) {
//            throw new RuntimeException("Такой пользователь уже существует!");
//        }
        // Хешируем пароль перед сохранением в базу данных
        //user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers(PageRequest pageRequest) {
        Page<User> page = userRepository.findAll(pageRequest);
        return page.getContent();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}


