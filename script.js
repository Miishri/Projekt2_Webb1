import {
    fetchCpu,
    fetchDisplay,
    fetchGpu,
    fetchMotherboard,
    fetchRam,
    fetchSsd,
    getAllComponents
} from "./model/ComponentFactory.js";

import {
    createProduct,
    createRecommendationProducts
} from "./model/Product.js";

const discountSlider = document.getElementById("slider");
const dots = document.querySelectorAll(".dot");
const products = document.querySelector(".products");
const categories = document.querySelectorAll(".category");

let imageIndex = 0;
let currentPage = 1;
const itemsPerPage = 12;
let productsLoaded = false;
let components = [];

async function fetchData() {
    components = await getAllComponents();
    createRecommendationProducts(components[3]);
    createRecommendationProducts(components[14]);
    createRecommendationProducts(components[34]);
    showPage(currentPage);
    productsLoaded = true;
}

fetchData();

function updateSlider() {
    imageIndex = (imageIndex + 1) % dots.length;
    discountSlider.scrollLeft = imageIndex * discountSlider.clientWidth;
    updateDot();
}

function updateDot() {
    dots.forEach(dot => dot.classList.remove("current-dot"));
    dots[imageIndex].classList.add("current-dot");
}

setInterval(updateSlider, 2000);

function loadComponent(component) {
    hidePaging();
    removeActiveCategories();
    const componentId = component.id;
    addActiveCategory(component);
    switch (componentId) {
        case "gpu":
            fetchAndDisplay(fetchGpu);
            break;
        case "cpu":
            fetchAndDisplay(fetchCpu);
            break;
        case "motherboard":
            fetchAndDisplay(fetchMotherboard);
            break;
        case "ram":
            fetchAndDisplay(fetchRam);
            break;
        case "monitor":
            fetchAndDisplay(fetchDisplay);
            break;
        case "ssd":
            fetchAndDisplay(fetchSsd);
            break;
    }
}

function fetchAndDisplay(fetchFunction) {
    fetchFunction().then(data => displayPagedProducts(data)).catch(error => console.error(error));
}

function addActiveCategory(element) {
    element.classList.add("active-category");
}

function removeActiveCategories() {
    categories.forEach(category => category.classList.remove("active-category"));
}

const disableCategories = () => categories.forEach(category => category.disabled = true);
const enableCategories = () => categories.forEach(category => category.disabled = false);

categories.forEach(category => {
    category.addEventListener('click', () => {
        if (!category.classList.contains('active-category')) {
            disableCategories();
            loadComponent(category);
            enableCategories();
        } else {
            removeActiveCategories();
            showPaging();
            showPage(currentPage);
        }
    });
});

function showPage(pageNumber) {
    const startIndex = (pageNumber - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    displayPagedProducts(components.slice(startIndex, endIndex));
}

function displayPagedProducts(splicedComponents) {
    const fragment = document.createDocumentFragment();
    splicedComponents.forEach(product => {
        const productElement = createProduct(product);
        fragment.appendChild(productElement);
    });
    products.innerHTML = '';
    products.appendChild(fragment);
}

for (let i = 1; i <= 6; i++) {
    const page = document.querySelector(`.page-${i}`);
    page.addEventListener('click', () => {
        showPage(i);
        currentPage = i;
    });
}

document.querySelector(".next").addEventListener('click', () => {
    if (currentPage < 6) {
        showPage(++currentPage);
    }
});

document.querySelector(".back").addEventListener('click', () => {
    if (currentPage > 1) {
        showPage(--currentPage);
    }
});

function hidePaging() {
    document.getElementById("products-paginator").style.display = 'none';
}

function showPaging() {
    document.getElementById("products-paginator").style.display = 'flex';
}
