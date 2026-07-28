package com.praveen.resumeanalyzer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    // -------------------------------
    // Common API Call
    // -------------------------------
    private String callGroq(String prompt) {

        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String safePrompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\r", "\\r")
                    .replace("\n", "\\n")
                    .replace("\t", "\\t");

            String requestBody = """
            {
              "model":"openai/gpt-oss-20b",
              "messages":[
                {
                  "role":"user",
                  "content":"%s"
                }
              ],
              "temperature":0.4
            }
            """.formatted(safePrompt);

            HttpEntity<String> entity =
                    new HttpEntity<>(requestBody, headers);

            String response = restTemplate.postForObject(
                    "https://api.groq.com/openai/v1/chat/completions",
                    entity,
                    String.class
            );

            JsonNode root = mapper.readTree(response);

            return root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        }
        catch (Exception e) {

            e.printStackTrace();

            return "";

        }

    }

    // -------------------------------
    // ATS Suggestions
    // -------------------------------

    public String getSuggestions(String resumeText) {

        String prompt = """
Analyze the following resume.

Return ONLY exactly 5 ATS improvement suggestions.

Rules:
- No Markdown
- No bold text
- No numbering
- No bullets
- One suggestion per line
- Keep each suggestion under 12 words

Resume:

%s
""".formatted(resumeText);

        String result = callGroq(prompt);

        if(result == null || result.isBlank()){

            return """
Add more projects
Add GitHub profile
Improve ATS keywords
Add certifications
Include measurable achievements
""";

        }

        return result;

    }

    // -------------------------------
    // AI Resume Summary
    // -------------------------------

    public String getResumeSummary(String resumeText){

        String prompt = """
Read this resume.

Write a professional resume summary.

Rules:
- 3 to 4 lines only
- Professional tone
- Mention strongest skills
- Mention career objective
- Plain text only
- No headings
- No Markdown

Resume:

%s
""".formatted(resumeText);

        String result = callGroq(prompt);

        if(result == null || result.isBlank()){

            return "Motivated software engineering student with strong Java and web development skills seeking opportunities to build scalable applications.";

        }

        return result;

    }

}