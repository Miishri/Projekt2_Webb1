import {
    fetchCpu,
    fetchDisplay,
    fetchGpu,
    fetchSsd,
    getAllComponents
} from "./model/ComponentFactory.js";
import {createProduct, createRecommendationProducts} from "./model/Product.js";

const discountSlider = document.getElementById("slider");
const products = document.querySelector(".products");
const categories = document.querySelectorAll(".category");
const cart = document.querySelector(".cart-logo");
const cartDropdown = document.getElementById("cart-dropdown");
const cartProducts = document.querySelector(".cart-products");
const deleteCartButton = document.getElementById("delete-cart");
const productCartCount = document.getElementById("product-count");
const totalProductPrice = document.getElementById("total");
const dots = document.querySelectorAll(".dot");


let totalPrice = 0;
let imageIndex = 0;
let productsLoaded = false;
let currentPage = 1;
let cartCount = 0;
let components = await getAllComponents();

setInterval(updateSlider, 2000);
function updateSlider() {
    imageIndex = (imageIndex + 1) % dots.length;
    discountSlider.scrollLeft = imageIndex * discountSlider.clientWidth;
    updateDot();
}
function updateDot() {
    dots.forEach((dot) =>  dot.classList.remove("current-dot"))
    dots[imageIndex].classList.add("current-dot")
}
updateDot();

const thirdProduct = components[28]

createRecommendationProducts(thirdProduct)

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
    categories.forEach((category) => category.classList.remove("active-category"))
}

const disableCategories = () => {categories.forEach(category => category.disabled = true)}
const enableCategories = () => {categories.forEach(category => category.disabled = false)}

function hidePaging() {
    document.getElementById("products-paginator").style.display = 'none'
}
function showPaging() {
    document.getElementById("products-paginator").style.display = 'flex'
}

if (!productsLoaded) {
    showPage(currentPage);
    productsLoaded = true;
}

function showPage(pageNumber) {
    const startIndex = (pageNumber - 1) * 8;
    const endIndex = startIndex + 8;
    displayPagedProducts(components.slice(startIndex, endIndex));
}
function displayPagedProducts(splicedComponents) {
    products.innerHTML = ''
    splicedComponents.forEach((product) => createProduct(product))
    toCart()
}
for (let i = 1; i <= 4; i++) {
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
function endOfNextPage(){if (currentPage <= 4 && currentPage + 1 !== 5) showPage(++currentPage)}
document.querySelector(".back").addEventListener('click', () => endOfBackPage())
function endOfBackPage() {
    if (currentPage > 1) showPage(--currentPage)
}

cart.addEventListener('click', () => {
    if (cartCount > 0) {
        toggleCartDropdown()
    }
})

function toggleCartDropdown() {
    cartDropdown.style.display = (cartDropdown.style.display === "none" || cartDropdown.style.display === "") ? "block" : "none";
}

function toCart() {
    visibleCartCount()
    document.querySelectorAll(".product-price").forEach((button) => {
        button.addEventListener('click', () => {
            if (cartCount < 5) {
                deleteCartButton.style.display = "flex"
                addToCart(button.id)
            }
        })
    })
}

function addToCart(componentId) {
    if (cartCount < 5) {
        const component = fetchComponentWithId(componentId);
        createDropdownProductElement(component);
        cartCount += 1;
        updateCartCount();
        addToLocalStorage(componentId)
    }
}

function fetchComponentWithId(productId) {
    return components.find(comp => comp.id === productId)
}
function updateCartCount() {
    productCartCount.style.display = "inline-block";
    productCartCount.textContent = cartCount
}

function createDropdownProductElement(component) {
    const cartProduct = document.createElement("div")
    cartProduct.classList.add("cart-product")

    const cartProductImage = document.createElement("img")
    cartProductImage.classList.add("cart-product-image")
    cartProductImage.src = component["image"][1]
    cartProductImage.height = 60
    cartProductImage.width = 60
    cartProduct.appendChild(cartProductImage)

    const cartProductName = document.createElement("div")
    cartProductName.classList.add("cart-product-name")
    cartProductName.textContent = component["title"]
    cartProduct.appendChild(cartProductName)

    const cartProductPrice = document.createElement("div")
    cartProductPrice.classList.add("cart-product-price")
    cartProductPrice.textContent = component["price"] + "$"
    cartProductName.appendChild(cartProductPrice)

    totalPrice += Number(component["price"])

    cartProducts.appendChild(cartProduct)
    calculateTotalPrice()
}

if (document.readyState !== 'loading') {
    console.log("hey")
    const localStorageProducts = localStorage.getItem("products")
    if (localStorageProducts) {
        deleteCartButton.style.display = "flex"
        let products = JSON.parse(localStorageProducts);
        cartCount = products.length
        productCartCount.style.display = "inline-block";
        productCartCount.textContent = cartCount

        products.forEach((productId) => {
            createDropdownProductElement(fetchComponentWithId(productId))
        })
        calculateTotalPrice()
    }
}

function visibleCartCount() {
    productCartCount.display = "inline-block"
}
function addToLocalStorage(componentId) {
    const localStorageProducts = localStorage.getItem("products")
    let products = localStorageProducts ? JSON.parse(localStorageProducts) : [];
    if (products.length < 6) {
        products.push(componentId)
    }
    localStorage.setItem("products", JSON.stringify(products));
}

function removeCartFromLocalStorage() {
    localStorage.removeItem("products")
}

function calculateTotalPrice() {
    totalProductPrice.textContent = "Total: " + Math.round(totalPrice) + " $"
}

deleteCartButton.addEventListener('click', () => resetCart())

function resetCart() {
    cartCount = 0;
    totalPrice = 0
    productCartCount.textContent = ""
    totalProductPrice.textContent = "Total: "
    cartDropdown.style.display = "none"
    cartProducts.innerHTML = ""
    removeCartFromLocalStorage()
}