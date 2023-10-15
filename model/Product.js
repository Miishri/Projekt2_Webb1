function createProduct(component) {
    const products = document.querySelector(".products")
    products.appendChild(createProductElement(component))
}
function createRecommendationProducts(component) {
    const products = document.querySelector(".recommended-products")
    products.appendChild(createProductElement(component))
}

function createProductElement(component) {
    const product = document.createElement("div")
    product.classList.add("product")
    product.id = component["id"]

    product.appendChild(createProductTitleElement(component))
    product.appendChild(createProductImageElement(component))
    product.appendChild(createProductDescriptionElement(component))
    product.appendChild(createProductProducerElement(component))
    product.appendChild(createProductRatingElement(component))
    product.appendChild(createProductPriceElement(component))

    return product
}

function createProductTitleElement(component) {
    const productTitle = document.createElement("h5")
    productTitle.classList.add("product-title")
    productTitle.textContent = component["title"]

    return productTitle
}

function createProductImageElement(component) {
    const productImage = document.createElement("img")
    productImage.classList.add("product-image")
    productImage.src = component["image"]
    return productImage
}

function createProductDescriptionElement(component) {
    const productDescription = document.createElement("div");
    productDescription.classList.add("product-description")
    productDescription.textContent = component["description"]
    return productDescription
}

function createProductProducerElement(component) {
    const productProducer = document.createElement("div")
    productProducer.classList.add("product-producer")
    productProducer.textContent = component["producer"]
    return productProducer
}

function createProductRatingElement(component) {
    const productRating = document.createElement("div")
    productRating.classList.add("product-rating")
    productRating.textContent = component["rating"]
    return productRating
}

function createProductPriceElement(component) {
    const productPrice = document.createElement("div")
    productPrice.classList.add("product-price")
    productPrice.textContent = (component["price"]) + " $"
    return productPrice
}

export {createProduct, createRecommendationProducts}