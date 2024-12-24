package ua.ihor.SpringBootSecurity4.controllers;


import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ua.ihor.SpringBootSecurity4.models.Application;
import ua.ihor.SpringBootSecurity4.models.MyUser;
import ua.ihor.SpringBootSecurity4.services.AppService;
import ua.ihor.SpringBootSecurity4.usersDetails.MyUserDetails;

import java.util.List;
import java.util.Map;

@RestController
@Component
@RequestMapping("rest")
@AllArgsConstructor
public class AppController {
    private AppService appService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome to unprotected page";
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        return Map.of("principal info", userDetails.getUsername() + " * " + userDetails.getAuthorities() + " * " + userDetails.getPassword());
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Application> detAll() {
        return appService.getApplications();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Application getById(@PathVariable int id) {
        return appService.getApplicationById(id);
    }

    @PostMapping("/new")
    public String add(@RequestBody MyUser myUser) {
        appService.addUser(myUser);
        return "User added";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin page";
    }
}
