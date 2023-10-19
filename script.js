import {
    fetchCpu,
    fetchDisplay,
    fetchGpu,
    fetchMotherboard,
    fetchRam, fetchSsd,
    getAllComponents
} from "./model/ComponentFactory.js";
import {createProduct, createRecommendationProducts} from "./model/Product.js";

const discountSlider = document.getElementById("slider");
const dots = document.querySelectorAll(".dot")
const products = document.querySelector(".products");
const categories = document.querySelectorAll(".category")

let imageIndex = 0
let productsLoaded = false;
const itemsPerPage = 12;
const components = await getAllComponents();
let currentPage = 1;

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

const firstProduct = components[3]
createRecommendationProducts(firstProduct)
const secondProduct = components[14]
createRecommendationProducts(secondProduct)
const thirdProduct = components[34]
createRecommendationProducts(thirdProduct)


function replaceComponentSource(image) {
    if (firstProduct["image"][1] === image) {
        return firstProduct["image"][0]
    }else if (secondProduct["image"][1] === image) {
        return secondProduct["image"][0]
    }else if (thirdProduct["image"][1] === image) {
        return thirdProduct["image"][0]
    }

    return image
}
categories.forEach((category) => {
    category.addEventListener('click', () => {
        if (!category.classList.contains('active-category')) {
            disableCategories();
            loadComponent(category);
            enableCategories();
        }else {
            removeActiveCategories()
            showPaging()
            showPage(currentPage)
        }
    })
})
const loadComponent = async function (component) {
    hidePaging()
    removeActiveCategories()
    const componentId = component.id
    addActiveCategory(component)
    switch (componentId) {
        case "gpu":
            displayPagedProducts(await fetchGpu())
            break;
        case "cpu":
            displayPagedProducts(await fetchCpu())
            break;
        case "motherboard":
            displayPagedProducts(await fetchMotherboard())
            break;
        case "ram":
            displayPagedProducts(await fetchRam())
            break;
        case "monitor":
            displayPagedProducts(await fetchDisplay())
            break;
        case "ssd":
            displayPagedProducts(await fetchSsd())
            break;
    }
}

function addActiveCategory(element) {
    element.classList.add("active-category")
}

function removeActiveCategories() {
    categories.forEach((category) => {
        category.classList.remove("active-category")
    })
}


const disableCategories = () => {
    categories.forEach(category => {
        category.disabled = true;
    });
};

const enableCategories = () => {
    categories.forEach(category => {
        category.disabled = false;
    });
};

if (!productsLoaded) {
    showPage(currentPage);
    productsLoaded = true;
}

function showPage(pageNumber) {
    const startIndex = (pageNumber - 1) * itemsPerPage;
    const endIndex = startIndex + itemsPerPage;
    displayPagedProducts(components.slice(startIndex, endIndex));
}
function displayPagedProducts(splicedComponents) {
    products.innerHTML = '';
    splicedComponents.forEach((product) => {
        createProduct(product);
    });
}
for (let i = 1; i <= 6; i++) {
    loadPageEvent(i)
}
function loadPageEvent(i) {
    const page = document.querySelector(`.page-${i}`);
    page.addEventListener('click', () => {
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
function hidePaging() {
    document.getElementById("products-paginator").style.display = 'none'
}
function showPaging() {
    document.getElementById("products-paginator").style.display = 'flex'
}
