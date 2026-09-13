# 🚀 AI Resume Analyzer

**🌟 Live Demo:** [https://ai-resume-analyzer-2-y5pq.onrender.com/](https://ai-resume-analyzer-2-y5pq.onrender.com/)

AI Resume Analyzer is a full-stack web application built with **Spring Boot**, **Java**, **H2 Database**, **HTML**, **CSS**, and **JavaScript**. It helps users analyze their resumes against a job description and provides an ATS-style report with AI-generated suggestions.

---

## ✨ Features

* 🔐 User Registration & Login
* 📄 Upload Resume (PDF/TXT)
* 🤖 AI Resume Analysis
* 📊 ATS Score Calculation
* 💼 Job Description Matching
* 🧠 Skill Detection
* ❌ Missing Skill Identification
* 💡 AI Suggestions for Improvement
* 📥 Download PDF Report
* 📜 Resume History
* 👤 User Dashboard & Profile

---

## 🛠️ Tech Stack

### Backend

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database
* Maven

### Frontend

* HTML5
* CSS3
* JavaScript
* Chart.js

### Libraries

* Apache PDFBox
* iTextPDF
* Jackson
* Lombok

### AI Integration

* Groq API

---

## 📁 Project Structure

```
resumeanalyzer
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.praveen.resumeanalyzer
│   │   │       ├── controller
│   │   │       ├── service
│   │   │       ├── repository
│   │   │       ├── model
│   │   │       ├── config
│   │   │       └── ResumeanalyzerApplication.java
│   │   │
│   │   └── resources
│   │       ├── static
│   │       ├── templates
│   │       └── application.properties
│
├── pom.xml
└── README.md
```

---

## ⚙️ Configuration

Update your **application.properties** before running the application.

```properties
spring.datasource.url=jdbc:h2:file:./data/resume_db
spring.datasource.username=sa
spring.datasource.password=

groq.api.key=YOUR_GROQ_API_KEY
```

---

## ▶️ Run the Project

Clone the repository:

```bash
git clone https://github.com/your-username/resumeanalyzer.git
```

Move into the project:

```bash
cd resumeanalyzer
```

Run the application:

Linux/Mac

```bash
./mvnw spring-boot:run
```

Windows

```bash
mvnw.cmd spring-boot:run
```

---

## 🌐 Access the Application

Open your browser:

```
http://localhost:8080
```

---

## 📌 Main Endpoints

| Method | Endpoint       | Description         |
| ------ | -------------- | ------------------- |
| POST   | /register      | Register User       |
| POST   | /login         | User Login          |
| GET    | /logout        | Logout User         |
| GET    | /check-session | Check Login Session |
| POST   | /analyze       | Analyze Resume      |
| GET    | /history       | Resume History      |
| GET    | /profile-data  | User Profile        |
| GET    | /download-pdf  | Download Report     |

---

## 🗄️ Database

This project uses **H2 File Database**.

The database is automatically created inside:

```
/data/resume_db.mv.db
```

You can access the H2 Console at:

```
http://localhost:8080/h2-console
```

JDBC URL:

```
jdbc:h2:file:./data/resume_db
```

Username

```
sa
```

Password

```
(blank)
```

---

## 🚀 Deployment

The project is ready for deployment on **Render**.

Deployment Steps:

1. Push the project to GitHub.
2. Create a new Web Service on Render.
3. Connect your GitHub repository.
4. Configure:

   * **Build Command**

     ```
     ./mvnw clean package
     ```
   * **Start Command**

     ```
     java -jar target/resumeanalyzer-0.0.1-SNAPSHOT.jar
     ```
5. Add the required environment variables, including your **Groq API Key**.
6. Deploy.

---

## 📸 Screenshots

* Login Page
* Register Page
* Dashboard
* Resume Analyzer
* ATS Report
* Resume History

---

## 👨‍💻 Developer

**Praveen Pichika**

* LinkedIn: https://www.linkedin.com/in/praveen-pichika-137b50351
* GitHub: https://github.com/PraveenPichika

---

## 📄 License

This project is developed for educational and portfolio purposes.
