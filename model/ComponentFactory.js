import {createProduct} from "./Product";

function fetchCpu(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            cpus.forEach(cpu => {
                const firstFiveCpus = cpu.slice(0, 7);
                firstFiveCpus.forEach(cpu => {
                    createProduct(cpu);
                })
            })
        })
}

function fetchGpu(){
    return fetch("Databases/gpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            const firstFiveGpus = gpus.slice(0, 7);
            firstFiveGpus.forEach(gpu => {
                createProduct(gpu);
            })
        })
}

function fetchMotherboard(){
    return fetch("Databases/motherboards_database.json")
        .then(res => res.json())
        .then(motherboards => {
            const firstFiveMotherboards = motherboards.slice(0, 7);
            firstFiveMotherboards.forEach(motherboard => {
                createProduct(motherboard);
            })
        })
}

function fetchDisplay(){
    return fetch("Databases/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            const firstFiveDisplays = displays.slice(0, 7);
            firstFiveDisplays.forEach(display => {
                createProduct(display);
            })
        })
}

function fetchRam(){
    return fetch("Databases/rams_database.json")
        .then(res => res.json())
        .then(rams => {
            const firstFiveRams = rams.slice(0, 7);
            firstFiveRams.forEach(ram => {
                createProduct(ram);
            })
        })
}

function fetchSsd(){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            const firstFiveSsds = ssds.slice(0, 7);
            firstFiveSsds.forEach(ssd => {
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