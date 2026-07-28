package com.praveen.resumeanalyzer.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Resume {


@ManyToOne
@JoinColumn(name = "user_id")
private User user;

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private String skills;

private int atsScore;

private LocalDateTime uploadedAt;

@Column(length = 5000)
private String content;

public Resume() {
    this.uploadedAt = LocalDateTime.now();
}

public Long getId() {
    return id;
}

public String getSkills() {
    return skills;
}

public void setSkills(String skills) {
    this.skills = skills;
}

public int getAtsScore() {
    return atsScore;
}

public void setAtsScore(int atsScore) {
    this.atsScore = atsScore;
}

public String getContent() {
    return content;
}

public void setContent(String content) {
    this.content = content;
}

public LocalDateTime getUploadedAt() {
    return uploadedAt;
}

public void setUploadedAt(LocalDateTime uploadedAt) {
    this.uploadedAt = uploadedAt;
}

public User getUser() {
    return user;
}

public void setUser(User user) {
    this.user = user;
}


}
