package com.example.portal.repository;

import com.example.portal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSeekerRepository  extends JpaRepository<JobSeekerProfile,Integer> {
}
