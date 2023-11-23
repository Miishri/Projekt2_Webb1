import {addToCart, findProductById, removeFromCart} from "./ProductApi.js";

async function createCartProduct(product) {
    displayBuy()
    displayCount()
    
    const fetchedProduct = await findProductById(product)
    
    if (fetchedProduct) {
        product = fetchedProduct
    }

    const productId = generateUniqueId(product)
    const cartProducts = document.querySelector(".cart-products")

    const cart = createCart(productId)
    cart.appendChild(createImageElement(product))
    cart.appendChild(createTitleElement(product))
    cart.appendChild(createPriceElement(product))
    cart.appendChild(createDeleteElement(product, productId))
    cartProducts.appendChild(cart)

    addToCart(productId, product["price"])
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

    deleteButton.addEventListener("click", () => {
        removeFromCart(productId)
    })

    return deleteButton
}

function displayBuy() {
    document.querySelector(".cart-buy").style.display = "inline-block"
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
function displayCount() {
    document.getElementById("cart-count").style.display = "inline-block"
}

function generateUniqueId(product) {
    if (product["id"]) {
        return product["id"] + ":" + Date.now()
    }
    return product + ":" + Date.now()
}

export {createCartProduct, displayCount}