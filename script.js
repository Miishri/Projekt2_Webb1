const images = document.querySelectorAll(".discount-image-slide")
const discountSlider = document.getElementById("image-sales");

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

function currentImage(dot) {
    switch (dot.id) {
        case "dot-1":
            changeCurrentDotClass(dot)
            break;
        case "dot-2":
            changeCurrentDotClass(dot)
            break;
        case "dot-3":
            changeCurrentDotClass(dot)
            break;
        case "dot-4":
            changeCurrentDotClass(dot)
            break;
        case "dot-5":
            changeCurrentDotClass(dot)
            break;
    }
}

function changeCurrentDotClass(dot) {
    dot.classList.add(".current-dot")
}