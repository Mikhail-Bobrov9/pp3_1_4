package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String showAllUsers(Model model) {
        model.addAttribute("showAllUsers", userService.getAllUsers());
        System.out.println(userService.getAllUsers());
        return "admin";
    }



    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "showUser";
    }
    @GetMapping("/showUser")
    public String showUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "showUser";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        System.out.println("new");
        return "/newUser";
    }

    @PostMapping
    public String save(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        System.out.println("save");
        return "redirect:/admin/";
    }

//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        model.addAttribute("user", userService.showUser(id));
//        System.out.println("edit");
//        return "/edit";
//    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.showUser(id));
        System.out.println("edit");
        return "/edit";
    }
    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.updateUser(user);
        System.out.println("Update");
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        System.out.println("delete user");
        return "redirect:/admin/";
    }
}
