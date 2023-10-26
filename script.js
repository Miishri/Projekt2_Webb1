import {loadProducts} from "./model/AccessoriesProducts.js";
import {createCartProduct} from "./model/CartProductFactory.js";
loadProducts()
slideCart()
closeCart()

const shoppingCart = document.getElementById("cart-background")

function slideCart() {
    const cartButton = document.querySelector(".cart-logo")
    cartButton.addEventListener('click', () => {
        if (shoppingCart.classList.contains("open")) {
            hideCart()
            enableScroll()
        }else {
            showCart()
            disableScroll()
        }
    })
}
function closeCart() {
    const closeCart = document.getElementById("cart-close")
    closeCart.addEventListener('click', () => {
        hideCart()
        enableScroll()
    })
}
function hideCart() {
    shoppingCart.classList.remove('open')
}
function showCart() {
    shoppingCart.classList.add('open');
}
function disableScroll() {
    document.body.style.overflow = 'hidden';
}
function enableScroll() {
    document.body.style.overflow = 'visible';
}

const productCartCount = document.getElementById("cart-count")
const cartProducts = document.querySelector(".cart-products")
const totalProductPrice = document.getElementById("total")
let totalPrice = 0
function addToLocalStorage(componentId) {
    const localStorageProducts = localStorage.getItem("products")
    let products = localStorageProducts ? JSON.parse(localStorageProducts) : [];
    if (products.length < 9) {
        products.push(componentId)
    }
    localStorage.setItem("products", JSON.stringify(products));
}
function removeFromStorage(componentId) {
    const localStorageProducts = localStorage.getItem("products")
    let products = localStorageProducts ? JSON.parse(localStorageProducts) : [];
    if (products.length < 9) {
        products = products.filter(productId => productId !== componentId);
    }
    localStorage.setItem("products", JSON.stringify(products));
}

function showPrice(price) {
    totalProductPrice.textContent = Math.round(price) + " $"
}
function deductPrice(price) {
    const existingTotal = totalProductPrice.textContent.slice(0, totalProductPrice.textContent.length-1).trim()
    if (existingTotal) {
        let total = existingTotal - price
        totalProductPrice.textContent = Math.round(total) + " $"
    }else {
        totalProductPrice.textContent = ""
    }
}


if (document.readyState !== 'loading') {
    const localStorageProducts = localStorage.getItem("products")
    if (localStorageProducts) {
        let products = JSON.parse(localStorageProducts);
        productCartCount.textContent = products.length
        productCartCount.style.display = "inline-block";

        cartProducts.forEach((productId) => {
            createCartProduct(productId)
        })
    }
}

document.querySelectorAll(".cart-product-delete").forEach((btn) => {
    btn.addEventListener("click", () => {

    })
})
