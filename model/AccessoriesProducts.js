import {fetchGamingBundles, fetchPrebuiltPcs, getAllComponents} from "./ComponentFactory.js";
import {createAccessory} from "./ProductFactory.js";

function loadProducts() {
    loadBundles()
    loadPrebuiltPcs()
    loadComponents()
}

async function loadBundles() {
    const gamingBundlesJson = await fetchGamingBundles()
    gamingBundlesJson.forEach((bundle) => {
        createBundleElement(bundle)
    })
}
async function loadPrebuiltPcs() {
    const prebuiltPcs = await fetchPrebuiltPcs()
    prebuiltPcs.forEach((pc) => {
        createPrebuiltElement(pc)
    })
}

async function loadComponents() {
    const components = await getAllComponents()
    components.forEach((component) => {
        createComponentElement(component)
    })
}

function createBundleElement(product) {
    const gamingBundles = document.getElementById("gaming-bundles")
    gamingBundles.appendChild(createAccessory(product))
}

function createPrebuiltElement(product) {
    const prebuiltPcs = document.getElementById("prebuilt-pcs")
    prebuiltPcs.appendChild(createAccessory(product))
}

function createComponentElement(product) {
    const component = document.getElementById("components")
    component.appendChild(createAccessory(product))
}

export {loadProducts}