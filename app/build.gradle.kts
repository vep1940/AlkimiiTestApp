plugins {
    id("vep1940.android.app")
    id("vep1940.android.compose")
    id("vep1940.unit.test")
    id("vep1940.android.test")
}

android {

    namespace = "com.vep1940.alkimiitestapp"

    defaultConfig {

        applicationId = "com.vep1940.alkimiitestapp"

    }
}

dependencies {

    implementation(projects.presentation)
    implementation(projects.domain)
    implementation(projects.data)
    implementation(projects.lang)

}