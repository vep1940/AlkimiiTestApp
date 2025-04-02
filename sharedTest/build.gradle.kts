plugins {
    id("vep1940.android.library")
}

android {
    namespace = "com.vep1940.alkimiitestapp.sharedtest"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.lang)

    implementation(libs.junit5.api)
    implementation(libs.coroutine.test)
}