document.addEventListener("DOMContentLoaded", function() {
    const ord = document.querySelector(".order-button");
    const sbm = document.querySelector(".submit-button");
    ord.addEventListener("click", showOrHideForm);
    sbm.addEventListener("click", showOrHideForm);
});


function showOrHideForm() {
    const overlay = document.getElementById("overlay-form-container");
    const activeParts = document.querySelectorAll(".subject-to-overlay");
    if (activeParts !== null && activeParts.length !== 0) {
        for (let part of activeParts) {
            part.classList.toggle("inactive");
        }
    }
    overlay.classList.toggle("show");
}