import {
    fetchCpu,
    fetchDisplay,
    fetchGpu,
    fetchMotherboard,
    fetchRam, fetchSsd,
    getAllComponents
} from "./model/ComponentFactory.js";
import {createProduct, createRecommendationProducts} from "./model/Product.js";

const productsContainer = document.querySelector(".products");
const paginator = document.getElementById("products-paginator");

const categoryContainer = document.querySelector(".categories");
categoryContainer.addEventListener('click', handleCategoryClick);

let currentPage = 1;
const itemsPerPage = 12;
let productsLoaded = false;
let components = [];

async function initializeComponents() {
    components = await getAllComponents();
    showPage(currentPage);
    productsLoaded = true;
}

function handleCategoryClick(event) {
    const category = event.target;
    if (category.classList.contains('category') && !category.classList.contains('active-category')) {
        hidePaging();
        removeActiveCategories();
        loadComponent(category);
        addActiveCategory(category);
        showPaging();
    }
}

async function loadComponent(component) {
    let componentData = [];
    switch (component.id) {
        case "gpu":
            componentData = await fetchGpu();
            break;
        case "cpu":
            componentData = await fetchCpu();
            break;
        case "motherboard":
            componentData = await fetchMotherboard();
            break;
        case "ram":
            componentData = await fetchRam();
            break;
        case "monitor":
            componentData = await fetchDisplay();
            break;
        case "ssd":
            componentData = await fetchSsd();
            break;
    }
    displayPagedProducts(componentData);
}

function displayPagedProducts(componentData) {
    const fragment = document.createDocumentFragment();
    productsContainer.innerHTML = '';
    componentData.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage).forEach((product) => {
        const productElement = createProduct(product);
        fragment.appendChild(productElement);
    });
    productsContainer.appendChild(fragment);
}

function showPage(pageNumber) {
    currentPage = pageNumber;
    displayPagedProducts(components);
}

function addActiveCategory(element) {
    removeActiveCategories();
    element.classList.add("active-category");
}

function removeActiveCategories() {
    document.querySelectorAll('.category').forEach((category) => {
        category.classList.remove("active-category");
    });
}

function hidePaging() {
    paginator.style.display = 'none';
}

function showPaging() {
    paginator.style.display = 'flex';
}

initializeComponents();
