plugins {
    id("vep1940.android.library")
    id("vep1940.graphql")
    id("vep1940.sql.delight")
    id("vep1940.unit.test")
}

android {
    namespace = "com.vep1940.alkimiitestapp.data"
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

sqldelight {
    databases {
        create("FavCharacterDatabase") {
            packageName.set("com.vep1940.alkimiitestapp")
        }
    }
}

dependencies {

    implementation(projects.domain)
    implementation(projects.lang)

    testImplementation(projects.sharedTest)
}
