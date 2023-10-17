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
    
    createRecommendationProducts(singleCpu[3])
    createRecommendationProducts(singleRam[5])
    createRecommendationProducts(singleMonitor[0])
}
populateRecommendedProducts()

const gpu = document.querySelector(".gpu")
const cpu = document.querySelector(".cpu")
const ssd = document.querySelector(".ssd")
const monitor = document.querySelector(".monitor")
const ram = document.querySelector(".ram")
const motherboard = document.querySelector(".motherboard")



let loadedProducts = false;
const productsDiv = document.querySelector(".products");
const components = await getAllComponents();
const itemsPerPage = 12;
let currentPage = 1;

if (!loadedProducts) {
    showPage(currentPage);
    loadedProducts = true;
}
function showPage(pageNumber) {
    const startIndex = (pageNumber - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    const pageComponents = components.slice(startIndex, endIndex);
    displayPagedProducts(pageComponents);
}
function displayPagedProducts(splicedComponents) {
    productsDiv.innerHTML = '';
    splicedComponents.forEach((product) => {
        createProduct(product);
    });
}
for (let i = 1; i <= 6; i++) {
    loadPageEvent(i)
}
function loadPageEvent(i) {
    const pageDiv = document.querySelector(`.page-${i}`);
    pageDiv.addEventListener('click', () => {
        showPage(i);
        currentPage = i;
    });
}

const next = document.querySelector(".next")
const back = document.querySelector(".back")

next.addEventListener('click', () => {
    if (currentPage <= 6 && currentPage + 1 !== 7) {
        showPage(++currentPage)
    }
})

back.addEventListener('click', () => {
    if (currentPage <= 6 && currentPage - 1 !== 0) {
        showPage(--currentPage)
    }
})


