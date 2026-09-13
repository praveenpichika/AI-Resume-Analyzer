package com.praveen.resumeanalyzer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.praveen.resumeanalyzer.model.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    // Get all resumes of a user
    List<Resume> findByUserId(Long userId);

    // Total resumes uploaded by the user
    long countByUserId(Long userId);

    // Average ATS Score
    @Query("""
        SELECT AVG(r.atsScore)
        FROM Resume r
        WHERE r.user.id = :userId
    """)
    Double findAverageATS(Long userId);

    // Best ATS Score
    @Query("""
        SELECT MAX(r.atsScore)
        FROM Resume r
        WHERE r.user.id = :userId
    """)
    Integer findBestATS(Long userId);

}