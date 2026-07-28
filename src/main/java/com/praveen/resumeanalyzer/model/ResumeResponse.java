package com.praveen.resumeanalyzer.model;

public class ResumeResponse {

    private int atsScore;

    private String detectedSkills;

    private String missingSkills;

    private String suggestions;

    private String message;
    private String summary;
    private int jobMatch;

    // ATS Score
    public int getAtsScore() {
        return atsScore;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    // Detected Skills
    public String getDetectedSkills() {
        return detectedSkills;
    }

    public void setDetectedSkills(String detectedSkills) {
        this.detectedSkills = detectedSkills;
    }

    // Missing Skills
    public String getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(String missingSkills) {
        this.missingSkills = missingSkills;
    }

    // AI Suggestions
    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    // Message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getSummary() {
    return summary;
}

public void setSummary(String summary) {
    this.summary = summary;
}
public int getJobMatch() {
    return jobMatch;
}

public void setJobMatch(int jobMatch) {
    this.jobMatch = jobMatch;
}
}