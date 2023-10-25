import {loadProducts} from "./model/AccessoriesProducts.js";
loadProducts()
slideCart()


function slideCart() {
    const cartButton = document.querySelector(".cart-logo")
    const shoppingCart = document.getElementById("shopping-cart")
    cartButton.addEventListener('click', () => {
        if (shoppingCart.style.right === "0px") {
            shoppingCart.style.right = "-100%"
        }else {
            shoppingCart.style.right = "0"
        }
    })
}