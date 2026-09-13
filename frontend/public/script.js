let chart;
let latestReport = {};
const API_BASE = window.location.hostname.includes("vercel.app") ? "/api" : "";
document.getElementById("resumeForm").addEventListener("submit", async function (e) {

    e.preventDefault();

    const fileInput = document.getElementById("file");

    if (fileInput.files.length === 0) {
        alert("Please select a resume.");
        return;
    }

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);
    formData.append(
        "jobDescription",
        document.getElementById("jobDescription").value
    );

    document.getElementById("loading").style.display = "block";

    try {

        const response = await fetch(`${API_BASE}/resume/upload`, {
            method: "POST",
            body: formData
        });

        const data = await response.json();
        latestReport = data;

        if (!response.ok) {
            throw new Error(data.message);
        }

        document.getElementById("loading").style.display = "none";

        //----------------------------
        // ATS SCORE
        //----------------------------

        const score = data.atsScore;

        document.getElementById("scoreText").innerHTML = score + "%";

        if (score >= 80) {

            document.getElementById("scoreText").style.color = "#16a34a";
            document.getElementById("scoreStatus").innerHTML = "🟢 Excellent Resume";

        }
        else if (score >= 60) {

            document.getElementById("scoreText").style.color = "#22c55e";
            document.getElementById("scoreStatus").innerHTML = "🟢 Good Resume";

        }
        else if (score >= 40) {

            document.getElementById("scoreText").style.color = "#f59e0b";
            document.getElementById("scoreStatus").innerHTML = "🟠 Average Resume";

        }
        else {

            document.getElementById("scoreText").style.color = "#ef4444";
            document.getElementById("scoreStatus").innerHTML = "🔴 Needs Improvement";

        }

        loadChart(score);

        //----------------------------
        // JOB MATCH
        //----------------------------

        if (document.getElementById("jobMatchText")) {

            document.getElementById("jobMatchText").innerHTML =
                (data.jobMatch || 0) + "%";

        }

        //----------------------------
        // DETECTED SKILLS
        //----------------------------

        let detectedHTML = "";

        data.detectedSkills.split(",").forEach(skill => {

            skill = skill.trim();

            if (skill !== "") {

                detectedHTML += `
                    <span class="skill-badge">${skill}</span>
                `;

            }

        });

        //----------------------------
        // MISSING SKILLS
        //----------------------------

        let missingHTML = "";

        data.missingSkills.split(",").forEach(skill => {

            skill = skill.trim();

            if (skill !== "") {

                missingHTML += `
                    <span class="missing-badge">${skill}</span>
                `;

            }

        });

        //----------------------------
        // AI Suggestions
        //----------------------------

        let suggestionHTML = "";

        let suggestions = data.suggestions
            .replace(/\*\*/g, "")
            .replace(/AI Suggestions:/gi, "");

        suggestions.split("\n").forEach(item => {

            item = item
                .replace(/^\d+\./, "")
                .replace(/^[-•]/, "")
                .trim();

            if (item !== "") {

                suggestionHTML += `
                    <div class="suggestion-card">
                        ✔ ${item}
                    </div>
                `;

            }

        });

        //----------------------------
        // REPORT
        //----------------------------

        document.getElementById("result").innerHTML = `

<div class="result-card">

    <div class="section">

        <h2>🛠 Detected Skills</h2>

        <div class="skill-container">

            ${detectedHTML}

        </div>

    </div>

    <div class="section">

        <h2>❌ Missing Skills</h2>

        <div class="skill-container">

            ${missingHTML}

        </div>

    </div>

    <div class="section">

        <h2>💡 AI Suggestions</h2>

        <div class="suggestion-container">

            ${suggestionHTML}

        </div>

    </div>

    <div class="section">

        <h2>📝 AI Resume Summary</h2>

        <div class="summary-card">

            ${data.summary || "Summary not available."}

        </div>

    </div>

</div>

`;
document.getElementById("downloadBtn").style.display = "block";
    }

    catch (error) {

        document.getElementById("loading").style.display = "none";

        document.getElementById("result").innerHTML = `
            <h2 style="color:red;">Error Uploading Resume</h2>
            <p>${error.message}</p>
        `;

        console.error(error);

    }

});

function loadChart(score) {

    const ctx = document.getElementById("atsChart");

    if (chart) {
        chart.destroy();
    }

    let chartColor = "#22c55e";

    if (score >= 90) {

        chartColor = "#22c55e";

    }
    else if (score >= 70) {

        chartColor = "#3b82f6";

    }
    else if (score >= 50) {

        chartColor = "#f59e0b";

    }
    else {

        chartColor = "#ef4444";

    }

    chart = new Chart(ctx, {

        type: "doughnut",

        data: {

            datasets: [{

                data: [score, 100 - score],

                backgroundColor: [
                    chartColor,
                    "#e2e8f0"
                ],

                borderWidth: 0

            }]

        },

        options: {

            responsive: true,
maintainAspectRatio: false,

            cutout: "75%",

            plugins: {

                legend: {

                    display: false

                }

            }

        }

    });

}
document.getElementById("downloadBtn").addEventListener("click", async () => {

    const params = new URLSearchParams({

        atsScore: latestReport.atsScore,

        jobMatch: latestReport.jobMatch,

        detectedSkills: latestReport.detectedSkills,

        missingSkills: latestReport.missingSkills,

        summary: latestReport.summary,

        suggestions: latestReport.suggestions

    });

    const response = await fetch(

        `${API_BASE}/resume/pdf`,

        {

            method: "POST",

            headers: {

                "Content-Type":

                "application/x-www-form-urlencoded"

            },

            body: params

        }

    );

    const blob = await response.blob();

    const url = window.URL.createObjectURL(blob);

    const a = document.createElement("a");

    a.href = url;

    a.download = "Resume_Report.pdf";

    document.body.appendChild(a);

    a.click();

    a.remove();

    window.URL.revokeObjectURL(url);

});