import {
    fetchCpu,
    fetchDisplay,
    fetchGpu,
    fetchMotherboard,
    fetchRam,
    fetchSsd,
    getAllComponents
} from "./model/ComponentFactory.js";

import { createProduct, createRecommendationProducts } from "./model/Product.js";

const discountSlider = document.getElementById("slider");
const dots = document.querySelectorAll(".dot");
const products = document.querySelector(".products");
const categories = document.querySelectorAll(".category");
const paginator = document.getElementById("products-paginator");

let imageIndex = 0;
let itemsPerPage = 12;
let currentPage = 1;
let productsLoaded = false;
let components = [];

async function initializeComponents() {
    components = await getAllComponents();
    createRecommendationProducts(components[3]);
    createRecommendationProducts(components[14]);
    createRecommendationProducts(components[34]);
    showPage(currentPage);
    productsLoaded = true;
}

function updateSlider() {
    imageIndex = (imageIndex + 1) % dots.length;
    discountSlider.scrollLeft = imageIndex * discountSlider.clientWidth;
    updateDot();
}

function updateDot() {
    dots.forEach((dot) => dot.classList.remove("current-dot"));
    dots[imageIndex].classList.add("current-dot");
}

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

async function fetchAndDisplay(fetchFunction) {
    const componentData = await fetchFunction();
    displayPagedProducts(componentData);
}

function addActiveCategory(element) {
    element.classList.add("active-category");
}

function removeActiveCategories() {
    categories.forEach((category) => category.classList.remove("active-category"));
}

function disableCategories() {
    categories.forEach(category => category.disabled = true);
}

function enableCategories() {
    categories.forEach(category => category.disabled = false);
}

function showPage(pageNumber) {
    currentPage = pageNumber;
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    displayPagedProducts(components.slice(startIndex, endIndex));
}

function displayPagedProducts(splicedComponents) {
    products.innerHTML = '';
    splicedComponents.forEach((product) => {
        createProduct(product);
    });
}

function hidePaging() {
    paginator.style.display = 'none';
}

function showPaging() {
    paginator.style.display = 'flex';
}

function handlePageNavigation(direction) {
    if (direction === 'next' && currentPage < 6) {
        showPage(++currentPage);
    } else if (direction === 'back' && currentPage > 1) {
        showPage(--currentPage);
    }
}

function handleCategoryClick(event) {
    const category = event.target;
    if (category.classList.contains('category') && !category.classList.contains('active-category')) {
        disableCategories();
        loadComponent(category);
        enableCategories();
        showPaging();
    } else {
        removeActiveCategories();
        showPaging();
        showPage(currentPage);
    }
}

discountSlider.addEventListener('click', updateSlider);
categories.forEach(category => category.addEventListener('click', handleCategoryClick));
document.querySelector(".next").addEventListener('click', () => handlePageNavigation('next'));
document.querySelector(".back").addEventListener('click', () => handlePageNavigation('back'));

initializeComponents();
