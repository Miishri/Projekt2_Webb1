import {getProductWithId} from "./ComponentFactory.js";

async function createCartProduct(productId) {
    const product = await getProductWithId(productId)
    const cartProducts = document.querySelector(".cart-products")
    const cartProduct = document.createElement("div")
    cartProduct.classList.add("cart-product")
    cartProduct.appendChild(createImageElement(product))
    cartProduct.appendChild(createTitleElement(product))
    cartProduct.appendChild(createPriceElement(product))
    cartProduct.appendChild(createDeleteElement(product))
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
function createDeleteElement(product) {
    const deleteButton = document.createElement("img")
    deleteButton.classList.add("cart-product-delete")
    deleteButton.id = product["id"]
    deleteButton.style.cursor = "pointer"
    deleteButton.src = "Images/logos/delete.svg"

    return deleteButton
}

function createPriceElement(product) {
    const price = document.createElement("div")
    price.classList.add("cart-product-price")
    price.textContent = product["price"] + "$"
    return price
}


export {createCartProduct}