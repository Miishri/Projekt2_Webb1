function fetchProcessors(){
    return fetch("database/processors.json")
        .then(response => response.json())
        .then(processors => {
            return processors
        })
}
function fetchGraphicCards(){
    return fetch("database/graphicCards.json")
        .then(response => response.json())
        .then(graphicCards => {
            return graphicCards
        })
}

function fetchMonitors(){
    return fetch("database/monitors.json")
        .then(response => response.json())
        .then(monitors => {
            return monitors
        })
}

function fetchStorageDevices(){
    return fetch("database/storageDevices.json")
        .then(response => response.json())
        .then(storageDevice => {
            return storageDevice
        })
}

function fetchBundles(){
    return fetch("database/bundles.json")
        .then(response => response.json())
        .then(bundles => {
            return bundles
        })
}


function fetchPrebuiltComputers(){
    return fetch("database/prebuiltComputers.json")
        .then(response => response.json())
        .then(prebuiltComputers => {
            return prebuiltComputers
        })
}

async function findProductById(productId) {
    const components = await getAllProducts()
    return components.filter((components) => components["id"] === productId)[0]
}
async function getAllProducts() {
    let components = []
    components = pushProduct(await fetchGraphicCards(), components)
    components = pushProduct(await fetchProcessors(), components)
    components = pushProduct(await fetchStorageDevices(), components)
    components = pushProduct(await fetchMonitors(), components)
    components = pushProduct(await fetchPrebuiltComputers(), components)
    components = pushProduct(await fetchBundles(), components)
    return components
}
function pushProduct(components, component) {
    component.forEach((component) => {
        components.push(component)
    })
    return components
}

function addToCart(productId) {
    const localStorageProducts = localStorage.getItem("products")
    let products = localStorageProducts ? JSON.parse(localStorageProducts) : [];
    if (products.length < 8) {
        products.push(productId)
    }
    localStorage.setItem("products", JSON.stringify(products));
    count(products)
}

function removeFromCart(productId) {
    const localStorageProducts = localStorage.getItem("products")
    let products = localStorageProducts ? JSON.parse(localStorageProducts) : [];
    if (products.length < 8) {
        products = products.filter(id => {
            if (id !== productId) {
                return true
            }else {
                const cart = document.getElementById(`${productId}`)
                cart.remove()
                return false;
            }
        });
    }
    localStorage.setItem("products", JSON.stringify(products))
    count(products)
}

function count(products) {
    const productCartCount = document.getElementById("cart-count")
    productCartCount.textContent = `${products.length}`

    if (productCartCount.textContent === "0") {
        productCartCount.style.display = "none"
        document.querySelector(".cart-buy").style.display = "none"
    }
}

function sliceProductId(product) {
    return product.split(":")[0]
}

function countPrice(price) {
    let count = price
    const totalProductPrice = document.getElementById("total")
    totalProductPrice.textContent = `${price + splitSign(price)}$`
}

function splitSign(priceTag) {
    return priceTag.splice(0, priceTag.length)
}

export {
    addToCart,
    removeFromCart,
    findProductById,
    fetchBundles,
    getAllProducts,
    fetchPrebuiltComputers
}