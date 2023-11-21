import {loadProducts} from "./model/AccessoryProductsApi.js";
import {createCartProduct, displayCount} from "./model/CartProductFactory.js";

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
const cartProducts = document.querySelectorAll(".cart-products")
async function loadCart() {
    localStorage.clear()//testing
    if (document.readyState !== 'loading') {
        const localStorageProducts = localStorage.getItem("products")
        if (localStorageProducts) {
            let products = JSON.parse(localStorageProducts);
            productCartCount.textContent = products.length
            displayCount()
            localStorage.clear()

            for (const productId of products) {
                await createCartProduct(productId)
            }
        }
    }
}

loadProducts()
slideCart()
closeCart()
loadCart()
