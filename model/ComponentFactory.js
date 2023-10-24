
function fetchCpu(){
    return fetch("Databases/components/cpus_database.json")
        .then(res => res.json())
        .then(cpus => {
            return cpus
        })
}

function fetchGpu(){
    return fetch("Databases/components/gpus_database.json")
        .then(res => res.json())
        .then(gpus => {
            return gpus
        })
}

function fetchDisplay(){
    return fetch("Databases/components/displays_database.json")
        .then(res => res.json())
        .then(displays => {
            return displays
        })
}

function fetchSsd(){
    return fetch("Databases/components/ssds_database.json")
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


function fetchPrebuiltPcs(){
    return fetch("Databases/accessories/prebuilt_pcs_products.json")
        .then(res => res.json())
        .then(pcs => {
            return pcs
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
    getAllComponents,
    fetchPrebuiltPcs
}