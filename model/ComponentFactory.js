import {createProduct} from "./Product";

function fetchCpu(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            cpus.forEach(cpu => {
                sliceComponentArray(cpu).forEach(cpu => {
                    createProduct(cpu);
                })
            })
        })
}

function fetchGpu(){
    return fetch("Databases/gpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            sliceComponentArray(gpu).forEach(gpu => {
                createProduct(gpu);
            })
        })
}

function fetchMotherboard(){
    return fetch("Databases/motherboards_database.json")
        .then(res => res.json())
        .then(motherboards => {
            sliceComponentArray(motherboards).forEach(motherboard => {
                createProduct(motherboard);
            })
        })
}

function fetchDisplay(){
    return fetch("Databases/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            sliceComponentArray(displays).forEach(display => {
                createProduct(display);
            })
        })
}

function fetchRam(){
    return fetch("Databases/rams_database.json")
        .then(res => res.json())
        .then(rams => {
            sliceComponentArray(rams).forEach(ram => {
                createProduct(ram);
            })
        })
}

function fetchSsd(){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            sliceComponentArray(ssds).forEach(ssd => {
                createProduct(ssd);
            })
        })
}

function sliceComponentArray(component) {
    return component.slice(0, 6);
}


export {fetchCpu,
    fetchSsd,
    fetchDisplay,
    fetchGpu,
    fetchRam,
    fetchMotherboard
}