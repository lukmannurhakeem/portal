package com.example.portal.services;

import com.example.portal.entity.RecruiterProfile;
import com.example.portal.repository.RecruiterProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class RecruiterProfileService {

    private final RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    public RecruiterProfileService(RecruiterProfileRepository recruiterProfileRepository) {
        this.recruiterProfileRepository = recruiterProfileRepository;
    }

    public Optional<RecruiterProfile> getOne(Integer id) {
        return recruiterProfileRepository.findById(id);
    }
}
