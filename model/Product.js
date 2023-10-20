function createProduct(component) {
    const products = document.querySelector(".products")
    products.appendChild(createProductElement(component))
}
function createRecommendationProducts(component) {
    const products = document.querySelector(".recommended-products")
    products.appendChild(createProductElement(component))
    return products
}

function createProductElement(component) {
    const product = document.createElement("div")
    product.classList.add("product")
    product.id = component["id"]

    product.appendChild(createProductImageElement(component))
    product.appendChild(createProductInfoElement(
        createProductTitleElement(component),
        createProductDescriptionElement(component),
        createProductPriceElement(component)))
    return product
}

function createProductInfoElement(title, description, price) {
    const productDetails = document.createElement("div")
    productDetails.classList.add("product-details")
    productDetails.appendChild(title)
    productDetails.appendChild(description)
    productDetails.appendChild(price)
    return productDetails
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
    productImage.src = component["image"][0]
    productImage.width = 150
    productImage.height = 150
    productImage.alt = component["title"]
    return productImage
}

function createProductDescriptionElement(component) {
    const productDescription = document.createElement("div");
    productDescription.classList.add("product-description")
    productDescription.textContent = component["description"]
    return productDescription
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
    productPrice.textContent = (component["price"]) + "$"
    return productPrice
}

export {createProduct, createRecommendationProducts}