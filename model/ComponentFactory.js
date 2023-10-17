import {createProduct} from "./Product.js";

function fetchCpu(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            return cpus
        })
}

function fetchGpu(){
    return fetch("Databases/gpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            return gpus
        })
}

function fetchMotherboard(){
    return fetch("Databases/motherboards_database.json")
        .then(res => res.json())
        .then(motherboards => {
            return motherboards
        })
}

function fetchDisplay(){
    return fetch("Databases/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            return displays
        })
}

function fetchRam(){
    return fetch("Databases/rams_database.json")
        .then(res => res.json())
        .then(rams => {
            return rams
        })
}

function fetchSsd(){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            return ssds
        })
}

async function getAllComponents() {
    let components = []
    components = compPutAndReturn(await fetchGpu(), components)
    components = compPutAndReturn(await fetchCpu(), components)
    components = compPutAndReturn(await fetchRam(), components)
    components = compPutAndReturn(await fetchSsd(), components)
    components = compPutAndReturn(await fetchMotherboard(), components)
    components = compPutAndReturn(await fetchDisplay(), components)

    return components
}

function compPutAndReturn(components, component) {
    component.forEach((component) => {
        components.push(component)
    })
    return components
}

export {fetchCpu,
    fetchSsd,
    fetchDisplay,
    fetchGpu,
    fetchRam,
    fetchMotherboard,
    getAllComponents
}