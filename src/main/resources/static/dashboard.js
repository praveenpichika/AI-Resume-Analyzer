// Resume Overview Chart
new Chart(document.getElementById("resumeChart"), {
    type: "doughnut",

    data: {
        labels: [
            "Technical Skills",
            "Projects",
            "Certifications",
            "Achievements"
        ],

        datasets: [{
            data: [70, 15, 10, 5],

            backgroundColor: [
                "#2563eb",
                "#16a34a",
                "#f59e0b",
                "#dc2626"
            ]
        }]
    },

    options: {
        responsive: true,

        plugins: {
            legend: {
                position: "bottom"
            }
        }
    }
});

// Load Resume History
async function loadHistory() {

    try {

        const baseUrl =
            window.location.hostname.includes("vercel.app")
                ? "/api"
                : "";

        const response =
            await fetch(`${baseUrl}/history`);

        if (!response.ok) {
            throw new Error("Failed to load history");
        }

        const data = await response.json();

        // Update resume count if element exists
        const countElement = document.getElementById("count");

        if (countElement) {
            countElement.innerText = data.length;
        }

        // Update history table if it exists
        const tableBody =
            document.querySelector("#historyTable tbody");

        if (tableBody) {

            let rows = "";

            data.forEach(item => {

                rows += `
                    <tr>
                        <td>${item.id}</td>
                        <td>${item.skills ?? "-"}</td>
                        <td>${item.atsScore ?? 0}</td>
                    </tr>
                `;
            });

            tableBody.innerHTML = rows;
        }

    } catch (error) {

        console.error("History Error:", error);

    }
}

loadHistory();