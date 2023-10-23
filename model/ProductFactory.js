function createAccessory(product) {
    const accessories = document.createElement("div")
    accessories.classList.add("accessories-product")
    accessories.appendChild(createImageElement(product))
    accessories.appendChild(createTitleElement(product))
    accessories.appendChild(createPriceElement(product))
    return accessories
}

function createImageElement(product) {
    const image = document.createElement("img")
    image.classList.add("accessories-product-image")
    image.src = product["image"]
    image.style.width = "35vw"
    return image
}

function createTitleElement(product) {
    const title = document.createElement("h5")
    title.classList.add("accessories-product-title")
    title.textContent = product["title"]
    return title
}

function createPriceElement(product) {
    const price = document.createElement("div")
    price.classList.add("accessories-product-price")
    price.id = product["id"]
    price.textContent = product["price"]
    return price
}

export {createAccessory}