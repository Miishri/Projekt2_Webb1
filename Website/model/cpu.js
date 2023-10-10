const products = document.querySelector(".products");

function createProduct(gpu) {
    const product = document.createElement("div")
    product.classList.add("product")
    product.id = gpu["id"]

    const productTitle = document.createElement("h5")
    productTitle.classList.add("product-title")

    const productImage = document.createElement("img")
    productImage.classList.add("product-image")

    const productDetails = document.createElement("div");
    productDetails.classList.add("product-details")

    product.addEventListener('click', () => {
        console.log(product)
        if (productDetails.style.display === 'none' || productDetails.style.display === '') {
            productDetails.style.display = 'block'
        } else {
            productDetails.style.display = 'none';
            console.log("here")
        }
        console.log("here")
    })
    
    //Object.keys(gpu).forEach(k => {})

    productImage.src = gpu["image"]
    productTitle.textContent = gpu["title"]

    product.appendChild(productImage)
    product.appendChild(productTitle)
    product.appendChild(productDetails)
    products.appendChild(product)
}

function fetchCpus(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            gpus.forEach(gpu => {
                createProduct(gpu);
            })
        })
}


export {fetchCpus}