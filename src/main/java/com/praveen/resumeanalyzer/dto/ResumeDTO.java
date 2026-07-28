package com.praveen.resumeanalyzer.dto;

public class ResumeDTO {

    private String message;

    public ResumeDTO() {
    }

    public ResumeDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}