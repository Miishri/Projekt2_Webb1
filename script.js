const images = document.querySelectorAll(".slider-images")
const discountSlider = document.getElementById("slider");
const dots = document.querySelectorAll(".dot")

let imageIndex = 0

function updateSlider() {
    imageIndex = (imageIndex + 1) % dots.length;
    discountSlider.scrollLeft = imageIndex * discountSlider.clientWidth;
    updateDot();
}

function updateDot() {
    dots.forEach((dot) => {
        dot.classList.remove("current-dot")
    })
    dots[imageIndex].classList.add("current-dot")
}

discountSlider.addEventListener('click', updateSlider)
discountSlider.scrollLeft = 0
updateDot();