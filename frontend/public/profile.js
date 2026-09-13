
async function loadProfile(){

    const response =
    await fetch(`${window.location.hostname.includes("vercel.app") ? "/api" : ""}/profile-data`);

    const data =
    await response.json();

    document.getElementById("name")
    .innerText = data.name;

    document.getElementById("email")
    .innerText = data.email;

    document.getElementById("totalResumes")
    .innerText = data.totalResumes;

    document.getElementById("averageScore")
    .innerText = data.averageScore + "%";
}

loadProfile();

