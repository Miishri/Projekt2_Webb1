import {loadProducts} from "./model/AccessoriesProducts.js";
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
