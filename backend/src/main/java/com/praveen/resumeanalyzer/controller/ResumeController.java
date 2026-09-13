package com.praveen.resumeanalyzer.controller;

import java.util.List;

import com.praveen.resumeanalyzer.model.Resume;
import com.praveen.resumeanalyzer.model.ResumeResponse;
import com.praveen.resumeanalyzer.model.User;
import com.praveen.resumeanalyzer.repository.ResumeRepository;
import com.praveen.resumeanalyzer.repository.UserRepository;
import com.praveen.resumeanalyzer.service.ResumeService;

import jakarta.servlet.http.HttpSession;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.praveen.resumeanalyzer.service.PdfService;
import com.praveen.resumeanalyzer.model.DashboardStats;
@RestController
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final PdfService pdfService;
    public ResumeController(

        ResumeService resumeService,

        ResumeRepository resumeRepository,

        UserRepository userRepository,

        PdfService pdfService

){

    this.resumeService = resumeService;
    this.resumeRepository = resumeRepository;
    this.userRepository = userRepository;
    this.pdfService = pdfService;

}

    @PostMapping("/resume/upload")
public ResponseEntity<ResumeResponse> uploadResume(
        @RequestParam("file") MultipartFile file,
        @RequestParam(value="jobDescription", required=false) String jobDescription,
        HttpSession session) {

        ResumeResponse response = new ResumeResponse();

        try {

            Long userId = (Long) session.getAttribute("userId");

            if (userId == null) {

                response.setMessage("Please login first.");

                return ResponseEntity.status(401).body(response);
            }

            User user = userRepository.findById(userId).orElse(null);

            if (user == null) {

                response.setMessage("User not found.");

                return ResponseEntity.status(401).body(response);
            }

            String content;

            if (file.getOriginalFilename() != null &&
                    file.getOriginalFilename().endsWith(".pdf")) {

                PDDocument document = Loader.loadPDF(file.getBytes());

                PDFTextStripper stripper = new PDFTextStripper();

                content = stripper.getText(document);

                document.close();

            } else {

                content = new String(file.getBytes());
            }
int atsScore = resumeService.calculateATSScore(content);

int jobMatch =
        resumeService.calculateJobMatch(content, jobDescription);

String detectedSkills =
        resumeService.extractSkills(content);

String missingSkills =
        resumeService.findMissingSkills(content);

String aiSuggestions = resumeService.getAISuggestions(content);
if (aiSuggestions == null) {
    aiSuggestions = "";
}

String summary = resumeService.getAISummary(content);
if (summary == null) {
    summary = "Summary not available.";
}
        if (detectedSkills == null)
    detectedSkills = "";

if (missingSkills == null)
    missingSkills = "";

if (aiSuggestions == null)
    aiSuggestions = "No suggestions available.";

if (summary == null)
    summary = "Summary not available.";

            Resume resume = new Resume();

            resume.setContent(content);
            resume.setSkills(detectedSkills);
            resume.setAtsScore(atsScore);
            resume.setUser(user);

            resumeRepository.save(resume);

            response.setAtsScore(atsScore);
response.setJobMatch(jobMatch);
response.setDetectedSkills(detectedSkills);
response.setMissingSkills(missingSkills);
response.setSuggestions(aiSuggestions);
response.setSummary(summary);
response.setMessage("Success");

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            e.printStackTrace();

            response.setMessage("Error : " + e.getMessage());

            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/resumes")
    public List<Resume> getUserResumes(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            return List.of();
        }

        return resumeRepository.findByUserId(userId);
    }

    @GetMapping("/history")
    public List<Resume> getHistory(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {

            return List.of();
        }

        return resumeRepository.findByUserId(userId);
    }
    @GetMapping("/dashboard/stats")
public DashboardStats dashboardStats(HttpSession session){

    DashboardStats stats = new DashboardStats();

    Long userId = (Long)session.getAttribute("userId");

    if(userId == null){
        return stats;
    }

    long total =
            resumeRepository.countByUserId(userId);

    Double avg =
            resumeRepository.findAverageATS(userId);

    Integer best =
            resumeRepository.findBestATS(userId);

    List<Resume> resumes =
            resumeRepository.findByUserId(userId);

    int totalSkills = 0;

    for(Resume r : resumes){

        if(r.getSkills()!=null){

            totalSkills +=
                    r.getSkills().split(",").length;

        }

    }

    stats.setTotalResumes((int)total);

    stats.setAverageATS(
            avg==null?0:Math.round(avg)
    );

    stats.setBestJobMatch(
            best==null?0:best
    );

    stats.setTotalSkills(totalSkills);

    return stats;

}
@PostMapping("/resume/pdf")
public ResponseEntity<byte[]> downloadReport(

        @RequestParam int atsScore,

        @RequestParam int jobMatch,

        @RequestParam String detectedSkills,

        @RequestParam String missingSkills,

        @RequestParam String summary,

        @RequestParam String suggestions

) throws Exception {

    byte[] pdf = pdfService.generateReport(

            atsScore,

            jobMatch,

            detectedSkills,

            missingSkills,

            summary,

            suggestions

    );

    return ResponseEntity.ok()

            .header(

                    "Content-Disposition",

                    "attachment; filename=Resume_Report.pdf"

            )

            .contentType(MediaType.APPLICATION_PDF)

            .body(pdf);

}
}