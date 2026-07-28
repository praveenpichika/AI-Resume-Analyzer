package com.praveen.resumeanalyzer.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import org.springframework.stereotype.Service;

@Service
public class PdfService {

    public byte[] generateReport(

            int atsScore,

            int jobMatch,

            String detectedSkills,

            String missingSkills,

            String summary,

            String suggestions

    ) throws IOException {

        PDDocument document = new PDDocument();

        PDPage page = new PDPage();

        document.addPage(page);

        PDPageContentStream content =
                new PDPageContentStream(document, page);

        PDType1Font titleFont =
                new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

        PDType1Font normalFont =
                new PDType1Font(Standard14Fonts.FontName.HELVETICA);

        float y = 760;

        content.beginText();

        content.setFont(titleFont,22);

        content.newLineAtOffset(50,y);

        content.showText("AI Resume Analyzer Report");

        content.endText();

        y -= 40;

        y = write(content,titleFont,16,50,y,"ATS Score : "+atsScore+"%");

        y = write(content,titleFont,16,50,y,"Job Match : "+jobMatch+"%");

        y -= 10;

        y = write(content,titleFont,15,50,y,"Detected Skills");

        y = write(content,normalFont,12,60,y,detectedSkills);

        y -= 10;

        y = write(content,titleFont,15,50,y,"Missing Skills");

        y = write(content,normalFont,12,60,y,missingSkills);

        y -= 10;

        y = write(content,titleFont,15,50,y,"AI Resume Summary");

        y = write(content,normalFont,12,60,y,summary);

        y -= 10;

        y = write(content,titleFont,15,50,y,"AI Suggestions");

        String[] list = suggestions.split("\n");

        for(String s : list){

            y = write(content,normalFont,12,60,y,"• "+s);

        }

        content.close();

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        document.save(out);

        document.close();

        return out.toByteArray();

    }

    private float write(

            PDPageContentStream content,

            PDType1Font font,

            int size,

            float x,

            float y,

            String text

    ) throws IOException {

        content.beginText();

        content.setFont(font,size);

        content.newLineAtOffset(x,y);

        content.showText(text);

        content.endText();

        return y-20;

    }

}