
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

function fetchDisplay(){
    return fetch("Databases/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            return displays
        })
}

function fetchSsd(){
    return fetch("Databases/ssds_database.json")
        .then(res => res.json())
        .then(ssds => {
            return ssds
        })
}

function fetchGamingBundles(){
    return fetch("Databases/accessories/gaming_bundles_products.json")
        .then(res => res.json())
        .then(bundles => {
            return bundles
        })
}



async function getAllComponents() {
    let components = []
    components = compPutAndReturn(await fetchGpu(), components)
    components = compPutAndReturn(await fetchCpu(), components)
    components = compPutAndReturn(await fetchSsd(), components)
    components = compPutAndReturn(await fetchDisplay(), components)

    return components
}

function compPutAndReturn(components, component) {
    component.forEach((component) => {
        components.push(component)
    })
    return components
}

export {
    fetchCpu,
    fetchSsd,
    fetchDisplay,
    fetchGpu,
    fetchGamingBundles,
    getAllComponents
}