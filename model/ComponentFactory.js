import {createProduct} from "./Product.js";

function sliceComponentArray(components) {
    return components.slice(0, 6);
}

function fetchCpu(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            return sliceComponentArray(cpus)
        })
}

function fetchGpu(className){
    return fetch("Databases/gpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            return sliceComponentArray(gpus)
        })
}

function fetchMotherboard(){
    return fetch("Databases/motherboards_database.json")
        .then(res => res.json())
        .then(motherboards => {
            return sliceComponentArray(motherboards)
        })
}

function fetchDisplay(){
    return fetch("Databases/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            return sliceComponentArray(displays)
        })
}

function fetchRam(){
    return fetch("Databases/rams_database.json")
        .then(res => res.json())
        .then(rams => {
            return sliceComponentArray(rams)
        })
}

function fetchSsd(className){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            return sliceComponentArray(ssds)
        })
}

export {fetchCpu,
    fetchSsd,
    fetchDisplay,
    fetchGpu,
    fetchRam,
    fetchMotherboard
}