package com.praveen.resumeanalyzer.controller;

import com.praveen.resumeanalyzer.model.Resume;
import com.praveen.resumeanalyzer.model.User;
import com.praveen.resumeanalyzer.repository.ResumeRepository;
import com.praveen.resumeanalyzer.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProfileController {


@Autowired
private UserRepository userRepository;

@Autowired
private ResumeRepository resumeRepository;

@GetMapping("/profile-data")
public Map<String, Object> getProfile(
        HttpSession session) {

    Map<String, Object> data =
            new HashMap<>();

    Long userId =
            (Long) session.getAttribute("userId");

    if(userId == null){

        data.put("name", "Not Logged In");
        data.put("email", "");
        data.put("totalResumes", 0);
        data.put("averageScore", 0);

        return data;
    }

    User user =
            userRepository.findById(userId)
                    .orElse(null);

    if(user == null){

        data.put("name", "User Not Found");
        data.put("email", "");
        data.put("totalResumes", 0);
        data.put("averageScore", 0);

        return data;
    }

    List<Resume> resumes =
            resumeRepository.findByUserId(userId);

    int totalResumes =
            resumes.size();

    int totalScore = 0;

    for(Resume resume : resumes){
        totalScore += resume.getAtsScore();
    }

    int averageScore = 0;

    if(totalResumes > 0){
        averageScore =
                totalScore / totalResumes;
    }

    data.put("name", user.getName());
    data.put("email", user.getEmail());
    data.put("totalResumes", totalResumes);
    data.put("averageScore", averageScore);

    return data;
}

}
