const images = document.querySelectorAll(".discount-image-slide")
const discountSlider = document.getElementById("discount-images");

setInterval(() => {
    slideImage()
}, 4000)

function slideImage() {
    const scrollAmount = discountSlider.scrollWidth / 5;
    if (equalsScrollWidth()) {
        discountSlider.scrollLeft = 0;
    } else {
        addScroll(scrollAmount)
    }
}
function equalsScrollWidth() {
    return (discountSlider.scrollLeft + discountSlider.clientWidth) === discountSlider.scrollWidth
}
function addScroll(scrollAmount) {
    discountSlider.scrollLeft += scrollAmount
}


const dots = document.querySelectorAll(".dot")

dots.forEach((dot) => {

})