import {fetchCpu, fetchDisplay, fetchGpu} from "./model/ComponentFactory.js";
import {createRecommendationProducts} from "./model/Product.js";

const discountSlider = document.getElementById("slider");
const dots = document.querySelectorAll(".dot")

let imageIndex = 0

setInterval(updateSlider, 2000)

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

updateDot();


async function populateRecommendedProducts() {
    const singleCpu = await fetchCpu()
    const singleGpu = await fetchGpu()
    const singleMonitor = await fetchDisplay()

    console.log(singleCpu[0])

    createRecommendationProducts(singleCpu[0])
    createRecommendationProducts(singleGpu[0])
    createRecommendationProducts(singleMonitor[0])
}

populateRecommendedProducts()