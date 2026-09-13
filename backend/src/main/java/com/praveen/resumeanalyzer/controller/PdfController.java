package com.praveen.resumeanalyzer.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController {

    @GetMapping("/download-pdf")
    public void downloadPdf(HttpServletResponse response)
            throws Exception {

        response.setContentType("application/pdf");

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=Resume_Report.pdf");

        Document document = new Document();

        PdfWriter.getInstance(
                document,
                response.getOutputStream());

        document.open();

        document.add(new Paragraph(
                "AI Resume Analyzer Report"));

        document.add(new Paragraph("ATS Score : 85"));

        document.add(new Paragraph(
                "Skills : Java, Spring Boot, MySQL"));

        document.add(new Paragraph(
                "Suggestions : Add GitHub, AWS"));

        document.close();
    }
}