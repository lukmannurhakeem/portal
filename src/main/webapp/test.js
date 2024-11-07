

let slideIndex = 0;
let slides = document.getElementsByClassName("slide");

// Function to show the next or previous slide
function moveSlide(step) {
    slideIndex += step;
    if (slideIndex >= slides.length) {
        slideIndex = 0;
    } else if (slideIndex < 0) {
        slideIndex = slides.length - 1;
    }
    showSlide();
}

// Function to display the current slide and hide others
function showSlide() {
    for (let i = 0; i < slides.length; i++) {
        slides[i].classList.remove("active");
    }
    slides[slideIndex].classList.add("active");
}

// Auto Slide
function autoSlide() {
    moveSlide(1); // Move to the next slide
    setTimeout(autoSlide, 5000); // Change every 5 seconds
}

// Start the auto slide when the page loads
window.onload = function() {
    showSlide();
    autoSlide();
}
