import {fetchCpu, fetchDisplay, fetchGpu, fetchRam, getAllComponents} from "./model/ComponentFactory.js";
import {createProduct, createRecommendationProducts} from "./model/Product.js";

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
    const singleRam = await fetchRam()
    const singleMonitor = await fetchDisplay()

    console.log(singleCpu[0])

    createRecommendationProducts(singleCpu[3])
    createRecommendationProducts(singleRam[5])
    createRecommendationProducts(singleMonitor[0])
}

populateRecommendedProducts()

const testProducts = await getAllComponents()

