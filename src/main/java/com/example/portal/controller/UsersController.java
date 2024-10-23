package com.example.portal.controller;

import com.example.portal.entity.UsersType;
import com.example.portal.entity.Users;
import com.example.portal.services.UsersService;
import com.example.portal.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    @Autowired
    public UsersController( UsersTypeService usersTypeService, UsersService usersService ) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersType = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersType);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users user, Model model) {
        System.out.println( "User :: " + user);

        Optional<Users> optionalUsers = usersService.findByEmail(user.getEmail());

        if (optionalUsers.isPresent()) {
            model.addAttribute("error", "Email is already registered");
            List<UsersType> usersType = usersTypeService.getAll();
            model.addAttribute("getAllTypes", usersType);
            model.addAttribute("user", new Users());
            return "register";
        }
        usersService.addNew(user);
        return "redirect:/dashboard/";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

}
