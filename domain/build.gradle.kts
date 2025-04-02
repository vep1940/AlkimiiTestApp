plugins {
    id("vep1940.kotlin.library")
    id("vep1940.unit.test")
}

dependencies {

    implementation(projects.lang)

    testImplementation(projects.sharedTest)

}