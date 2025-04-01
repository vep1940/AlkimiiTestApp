plugins {
    id("vep1940.kotlin.library")
    id("vep1940.graphql")
    id("vep1940.unit.test")
}

apollo {
    service("service") {
        packageName.set("com.vep1940.alkimiitestapp.data.graphql")
        introspection {
            endpointUrl.set("https://rickandmortyapi.com/graphql")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {

    implementation(projects.domain)
    implementation(projects.lang)

}
