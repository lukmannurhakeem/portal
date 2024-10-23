package com.example.portal.controller;

import com.example.portal.entity.Users;
import com.example.portal.repository.UsersRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recruiter-profile")
public class RecruiterProfileController {

    private final UsersRepository  usersRepository;


    public RecruiterProfileController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String recruiterProfile(Model model) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( !(authentication instanceof AnonymousAuthenticationToken) ) {
            String currentUserName = authentication.getName();
            Users users =  usersRepository.findByEmail(currentUserName).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        }
        return "recruiter-profile";
    }
}
