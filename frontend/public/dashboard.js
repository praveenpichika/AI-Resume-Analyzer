
new Chart(document.getElementById('resumeChart'),{

    type:'doughnut',

    data:{
        labels:[
            'Technical Skills',
            'Projects',
            'Certifications',
            'Achievements'
        ],

        datasets:[{
            data:[70,15,10,5],

            backgroundColor:[
                '#2563eb',
                '#16a34a',
                '#f59e0b',
                '#dc2626'
            ]
        }]
    },

    options:{
        responsive:true,

        plugins:{
            legend:{
                position:'bottom'
            }
        }
    }
});

async function loadHistory(){

    try{

        const response =
        await fetch(`${window.location.hostname.includes("vercel.app") ? "/api" : ""}/all`);

        const data =
        await response.json();

        document.getElementById("count")
        .innerText = data.length;

        let rows = "";

        data.forEach(item=>{

            rows += `
            <tr>
                <td>${item.id}</td>
                <td>${item.skills}</td>
                <td>${item.atsScore}</td>
            </tr>
            `;
        });

        document.querySelector(
        "#historyTable tbody"
        ).innerHTML = rows;

    }catch(error){

        console.log(error);

    }
}

loadHistory();

