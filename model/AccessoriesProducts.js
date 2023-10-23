import {fetchGamingBundles} from "./ComponentFactory.js";
import {createAccessory} from "./ProductFactory.js";

async function loadBundles() {
    const gamingBundlesJson = await fetchGamingBundles()
    gamingBundlesJson.forEach((bundle) => {
        createBundleElement(bundle)
    })
}

function createBundleElement(product) {
    const gamingBundles = document.getElementById("gaming-bundles")
    gamingBundles.appendChild(createAccessory(product))
}

export {loadBundles}