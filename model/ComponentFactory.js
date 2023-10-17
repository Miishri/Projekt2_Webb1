import {createProduct} from "./Product.js";

function fetchCpu(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            return cpus
        })
}

function fetchGpu(className){
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
            return sliceComponentArray(displays)
        })
}

function fetchRam(){
    return fetch("Databases/rams_database.json")
        .then(res => res.json())
        .then(rams => {
            return rams
        })
}

function fetchSsd(className){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            return ssds
        })
}

export {fetchCpu,
    fetchSsd,
    fetchDisplay,
    fetchGpu,
    fetchRam,
    fetchMotherboard
}