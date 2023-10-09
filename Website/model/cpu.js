const products = document.querySelector(".products");

function createProduct(gpu) {
    const product = document.createElement("div")
    product.classList.add("product")

    const productTitle = document.createElement("h5")
    productTitle.classList.add("product-title")

    const productImage = document.createElement("img")
    productImage.classList.add("product-image")


    //Object.keys(gpu).forEach(k => {})

    productImage.src = gpu["image"]
    productTitle.textContent = gpu["title"]

    product.appendChild(productImage);
    product.appendChild(productTitle)
    products.appendChild(product)
}

function fetchCpus(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            gpus.forEach(gpu => {
                createProduct(gpu);
                console.log(gpu)
            })
        })
}


    
