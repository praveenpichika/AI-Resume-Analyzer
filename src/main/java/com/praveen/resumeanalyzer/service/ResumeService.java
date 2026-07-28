package com.praveen.resumeanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResumeService {

    @Autowired
    private GroqService groqService;

    private final String[] requiredSkills = {
            "java",
            "spring boot",
            "mysql",
            "html",
            "css",
            "javascript",
            "git",
            "github",
            "docker",
            "aws"
    };

    // Calculate ATS Score
    public int calculateATSScore(String resumeText) {

        String text = resumeText.toLowerCase();

        int matchedSkills = 0;

        for (String skill : requiredSkills) {

            if (text.contains(skill)) {
                matchedSkills++;
            }

        }

        return (matchedSkills * 100) / requiredSkills.length;
    }

    // Detected Skills
    public String extractSkills(String resumeText) {

        String text = resumeText.toLowerCase();

        StringBuilder skills = new StringBuilder();

        for (String skill : requiredSkills) {

            if (text.contains(skill)) {

                if (skills.length() > 0) {
                    skills.append(",");
                }

                skills.append(skill);

            }

        }

        return skills.toString();
    }

    // Missing Skills
    public String findMissingSkills(String resumeText) {

        String text = resumeText.toLowerCase();

        StringBuilder missing = new StringBuilder();

        for (String skill : requiredSkills) {

            if (!text.contains(skill)) {

                if (missing.length() > 0) {
                    missing.append(",");
                }

                missing.append(skill);

            }

        }

        return missing.toString();
    }

    // AI Suggestions
    public String getAISuggestions(String resumeText) {

        return groqService.getSuggestions(resumeText);

    }
    // AI Resume Summary
public String getAISummary(String resumeText) {

    return groqService.getResumeSummary(resumeText);

}
public int calculateJobMatch(String resumeText, String jobDescription) {

    if(jobDescription == null || jobDescription.isBlank()){
        return 0;
    }

    resumeText = resumeText.toLowerCase();
    jobDescription = jobDescription.toLowerCase();

    String[] words = jobDescription
            .replaceAll("[^a-zA-Z ]", "")
            .split("\\s+");

    int total = 0;
    int matched = 0;

    for(String word : words){

        if(word.length() < 4)
            continue;

        total++;

        if(resumeText.contains(word)){
            matched++;
        }
    }

    if(total == 0)
        return 0;

    return (matched * 100) / total;
}

}