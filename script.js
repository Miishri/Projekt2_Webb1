import {
    fetchCpu,
    fetchDisplay,
    fetchGpu,
    fetchMotherboard,
    fetchRam,
    fetchSsd,
    getAllComponents
} from "./model/ComponentFactory.js";
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
await populateRecommendedProducts()

const gpu = document.querySelector(".gpu")
gpu.addEventListener('click', async () => {
    addActiveCategory(gpu)
    displayPagedProducts(await fetchGpu())
})  
const cpu = document.querySelector(".cpu")
cpu.addEventListener('click', async () => {
    addActiveCategory(cpu)
    displayPagedProducts(await fetchCpu())
})
const ssd = document.querySelector(".ssd")
ssd.addEventListener('click', async () => {
    addActiveCategory(ssd)
    displayPagedProducts(await fetchSsd())
})
const monitor = document.querySelector(".monitor")
monitor.addEventListener('click', async () => {
    addActiveCategory(monitor)
    displayPagedProducts(await fetchDisplay())
})
const ram = document.querySelector(".ram")
ram.addEventListener('click', async () => {
    addActiveCategory(ram)
    displayPagedProducts(await fetchRam())
})
const motherboard = document.querySelector(".motherboard")
motherboard.addEventListener('click', async () => {
    addActiveCategory(motherboard)
    displayPagedProducts(await fetchMotherboard())
})

function addActiveCategory(element) {
    hideAllPages()
    removeAllActiveCategories()
    element.classList.add("active-category")
}

function hideAllPages() {
    document.querySelectorAll(".products-paging").forEach((page) => {
        page.classList.add("hide-page")
    })
}

function removeAllActiveCategories() {
    document.querySelectorAll(".categories").forEach((cat) => {
        cat.classList.remove("active-category");
    });
}


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
document.querySelector(".next").addEventListener('click', () => endOfNextPage())
function endOfNextPage(){if (currentPage <= 6 && currentPage + 1 !== 7) showPage(++currentPage)}
document.querySelector(".back").addEventListener('click', () => endOfBackPage())
function endOfBackPage() {
    if (currentPage <= 6 && currentPage - 1 !== 0) {
        showPage(--currentPage)
    }
}
