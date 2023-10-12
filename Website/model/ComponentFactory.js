import {createProduct} from "./Product";

function fetchCpu(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            cpus.forEach(cpu => {
                createProduct(cpu);
            })
        })
}

function fetchGpu(){
    return fetch("Databases/gpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            gpus.forEach(gpu => {
                createProduct(gpu);
            })
        })
}

function fetchMotherboard(){
    return fetch("Databases/motherboards_database.json")
        .then(res => res.json())
        .then(motherboards => {
            motherboards.forEach(motherboard => {
                createProduct(motherboard);
            })
        })
}

function fetchDisplay(){
    return fetch("Databases/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            displays.forEach(display => {
                createProduct(display);
            })
        })
}

function fetchRam(){
    return fetch("Databases/rams_database.json")
        .then(res => res.json())
        .then(rams => {
            rams.forEach(ram => {
                createProduct(ram);
            })
        })
}

function fetchSsd(){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            ssds.forEach(ssd => {
                createProduct(ssd);
            })
        })
}


export {fetchCpu,
    fetchSsd,
    fetchDisplay,
    fetchGpu,
    fetchRam,
    fetchMotherboard
}