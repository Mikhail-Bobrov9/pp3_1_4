package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String name);

    void saveUser(User user);

    User showUser(Long id);

    void deleteUserById(Long id);

    List<User> getAllUsers();

    void updateUser(User user);
}
