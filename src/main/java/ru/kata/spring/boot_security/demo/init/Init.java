package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

@Component
public class Init {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        Role role = new Role("ROLE_USER");
        roleRepository.save(role);

        Role role1 = new Role("ROLE_ADMIN");
        roleRepository.save(role1);

        User user = new User();
        user.setUsername("user");
        user.setEmail("111@mail.ru");
        user.setPassword("user");
        user.setRoles(Collections.singletonList(role));
        userService.saveUser(user);

        User user1 = new User();
        user1.setUsername("admin");
        user1.setEmail("222@mail.ru");
        user1.setPassword("admin");
        user1.setRoles(Collections.singletonList(role1));
        userService.saveUser(user1);

        User user2 = new User();
        user2.setUsername("usertest");
        user2.setEmail("xxx@mail.ru");
        user2.setPassword("usertest");
        user2.setRoles(List.of(role, role1));
        userService.saveUser(user2);
    }
}
