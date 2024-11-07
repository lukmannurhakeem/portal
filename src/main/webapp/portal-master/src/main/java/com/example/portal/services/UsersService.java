package com.example.portal.services;

import com.example.portal.entity.JobSeekerProfile;
import com.example.portal.entity.RecruiterProfile;
import com.example.portal.entity.Users;
import com.example.portal.repository.JobSeekerRepository;
import com.example.portal.repository.RecruiterProfileRepository;
import com.example.portal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerRepository jobSeekerRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;

    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UsersService(UsersRepository usersRepository,JobSeekerRepository jobSeekerRepository,RecruiterProfileRepository recruiterProfileRepository,PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jobSeekerRepository = jobSeekerRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));

        users.setPassword(passwordEncoder.encode(users.getPassword()));

        Users savedUser = usersRepository.save(users);

        int userTypeId = users.getUserTypeId().getUserTypeId();
        if (userTypeId == 1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        }else {
            jobSeekerRepository.save(new JobSeekerProfile(savedUser));
        }

        return usersRepository.save(users);
    }

    public Optional<Users> findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (!(authentication instanceof AnonymousAuthenticationToken)) {
           String name = authentication.getName();
           Users user = usersRepository.findByEmail(name).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
           int userId = user.getUserId();

           if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))) {
               return recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
           }
           else{
               return jobSeekerRepository.findById(userId).orElse(new JobSeekerProfile());
           }
       }
       return null;
    }
}