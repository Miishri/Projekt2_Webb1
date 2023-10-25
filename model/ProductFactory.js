import {createCartProduct} from "./CartProductFactory.js";

function createAccessory(product) {
    const accessories = document.createElement("div")
    accessories.classList.add("accessories-product")
    accessories.appendChild(createImageElement(product))
    accessories.appendChild(createTitleElement(product))
    accessories.appendChild(createPriceElement(product))
    return accessories
}

function createImageElement(product) {
    const pictureElement = document.createElement("picture")
    
    const sourceTablet = document.createElement("source")
    sourceTablet.media = "(min-width: 641px)"
    sourceTablet.srcset = product["image"]
    sourceTablet.style.width = "34vw"
    sourceTablet.style.height = "auto"
    
    const sourceLaptop = document.createElement("source")
    sourceLaptop.media = "(min-width: 1025px)"
    sourceLaptop.srcset = product["image"]
    sourceLaptop.style.width = "14vw"
    sourceLaptop.style.height = "auto"
    
    const image = document.createElement("img")
    image.classList.add("accessories-product-image")
    image.src = product["image"]
    image.alt = product["title"]
    sourceLaptop.style.width = "30vw"
    sourceLaptop.style.height = "auto"

    pictureElement.appendChild(sourceTablet)
    pictureElement.appendChild(sourceLaptop)
    pictureElement.appendChild(image)
    return pictureElement
}

function createTitleElement(product) {
    const title = document.createElement("p")
    title.classList.add("accessories-product-title")
    title.textContent = product["title"]
    return title
}

function createPriceElement(product) {
    const price = document.createElement("div")
    price.classList.add("accessories-product-price")
    price.id = product["id"]
    price.textContent = product["price"] + "$"

    price.addEventListener('click', () => {
        createCartProduct(product["id"])
    })
    return price
}

export {createAccessory}