const images = document.querySelectorAll(".discount-image-slide")
const discountSlider = document.querySelector(".discount-images");

setInterval(() => {
    const scrollAmount = discountSlider.scrollWidth / 5;
    if ((discountSlider.scrollLeft + discountSlider.clientWidth) === discountSlider.scrollWidth) {
        discountSlider.scrollLeft = 0;
    } else {
        discountSlider.scrollLeft += scrollAmount;
    }

}, 4000)