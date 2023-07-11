package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.RoleServiceImp;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;
    private final RoleServiceImp roleServiceImp;

    @Autowired
    public AdminController(UserServiceImp userServiceImp,RoleServiceImp roleServiceImp) {
        this.userServiceImp = userServiceImp;
        this.roleServiceImp = roleServiceImp;


    }


    @GetMapping("/")
    public String showAllUsers(Model model, Principal principal) {
        User user = userServiceImp.findByUsername(principal.getName());
        model.addAttribute("showAllUsers", userServiceImp.getAllUsers());
        model.addAttribute("user", user);
        System.out.println(userServiceImp.getAllUsers());
        return "admin";
    }



    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userServiceImp.showUser(id));
        return "showUser";
    }
    @GetMapping("/showUser")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userServiceImp.findByUsername(principal.getName()));
        return "showUser";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleServiceImp.getAllRoles());
        System.out.println("new");
        return "admin";
    }

    @PostMapping
    public String save(@ModelAttribute("user") @Valid User user) {

        if (!userServiceImp.isUsernameUnique(user.getUsername())) {
            System.out.println("Имя не  уникально!");
            return "redirect:/admin/";
        }

        userServiceImp.saveUser(user);
        System.out.println("save");
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userServiceImp.showUser(id));
        System.out.println("edit");
        return "admin";
    }
    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") @Valid User user,
                         @PathVariable("id") Long id) {
        userServiceImp.updateUser(user);
        System.out.println("Update");
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userServiceImp.deleteUserById(id);
        System.out.println("delete user");
        return "redirect:/admin/";
    }
}
