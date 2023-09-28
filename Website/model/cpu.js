function print(){
    return fetch("Databases/cpus_database.json")
        .then(res => res.json())
}

print().then(json => {
    console.log(json)
})