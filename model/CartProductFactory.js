import {findProductById} from "./ProductApi.js";

async function createCartProduct(productId) {
    displayBuy()
    displayCount()
    
    const product = await findProductById(productId)
    const cartProducts = document.querySelector(".cart-products")

    const cartProduct = createCart(productId)
    cartProduct.appendChild(createImageElement(product))
    cartProduct.appendChild(createTitleElement(product))
    cartProduct.appendChild(createPriceElement(product))
    cartProduct.appendChild(createDeleteElement(product, productId))
    cartProducts.appendChild(cartProduct)
}

function createImageElement(product) {
    const image = document.createElement("img")
    image.classList.add("cart-product-image")
    image.src = product["image"]
    image.alt = product["title"]
    return image
}

function createTitleElement(product) {
    const title = document.createElement("p")
    title.classList.add("cart-product-title")
    title.textContent = product["title"]
    return title
}
function createDeleteElement(product, productId) {
    const deleteButton = document.createElement("img")
    deleteButton.classList.add("cart-product-delete")
    deleteButton.id = productId
    deleteButton.src = "https://svgshare.com/i/zdP.svg"
    
    
    return deleteButton
}

function displayBuy() {
    document.querySelector(".cart-buy").classList.toggle("show")
}
function hideBuy() {
    document.querySelector(".cart-buy").classList.toggle("hide")
}

function displayCount() {
    document.getElementById("cart-count").classList.toggle("show")
}
function hideCount() {
    document.getElementById("cart-count").classList.toggle("hide")
}

function createCart(uniqueId) {
    const cartProduct = document.createElement("div")
    cartProduct.classList.add("cart-product")
    cartProduct.id = uniqueId
    return cartProduct
}

function createPriceElement(product) {
    const price = document.createElement("div")
    price.classList.add("cart-product-price")
    price.textContent = product["price"] + "$"
    return price
}

export {createCartProduct}