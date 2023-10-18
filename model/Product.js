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

    product.appendChild(createProductImageElement(component))
    product.appendChild(createProductTitleElement(component))
    product.appendChild(createProductDescriptionElement(component))
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
    productImage.src = component["image"][1]
    productImage.alt = component["title"]
    productImage.width = 150
    productImage.height = 150
    return productImage
}

function createProductDescriptionElement(component) {
    const productDescription = document.createElement("div");
    productDescription.classList.add("product-description")
    productDescription.classList.add("product-description-hidden")
    productDescription.textContent = component["description"]
    return productDescription
}

function createProductRatingElement(component) {
    const productRating = document.createElement("div")
    productRating.classList.add("product-rating")
    productRating.classList.add("product-rating-hidden")
    productRating.textContent = component["rating"]
    return productRating
}

function createProductPriceElement(component) {
    const productPrice = document.createElement("div")
    productPrice.classList.add("product-price")
    productPrice.textContent = (component["price"]) + "$"
    return productPrice
}

export {createProduct, createRecommendationProducts}