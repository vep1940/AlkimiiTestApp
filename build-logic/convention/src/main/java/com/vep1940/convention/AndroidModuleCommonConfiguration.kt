package com.vep1940.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.androidModuleCommonConfiguration(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {

        compileSdk = libs.getVersion("compileSdk").toInt()

        defaultConfig {
            minSdk = libs.getVersion("minSdk").toInt()
        }

        when (this) {
            is LibraryExtension -> {
                buildTypes {
                    release {
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }

            is BaseAppModuleExtension -> {
                buildTypes {
                    release {
                        isShrinkResources = true
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }

        val javaVersion = JavaVersion.VERSION_21

        compileOptions {
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        with(extensions.getByType<KotlinAndroidProjectExtension>()){
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
            }
        }

        packaging {
            resources {
                excludes.add("META-INF/*.kotlin_module")
                excludes.add("META-INF/LICENSE*")
            }
        }
    }

    dependencies {
        add("implementation", libs.getLibrary("core-ktx"))
        add("implementation", libs.getLibrary("lifecycle-ktx"))

        add("implementation", libs.getLibrary("coroutine-android"))

        add("implementation", libs.getLibrary("koin-android"))
        add("testImplementation", libs.getLibrary("koin-test"))
    }
}

fun Project.configureForAndroidModule(block: (CommonExtension<*, *, *, *, *, *>) -> Unit) {
    val androidAppPluginId = libs.getPluginId("android-application")
    val androidLibPluginId = libs.getPluginId("android-library")

    when {
        pluginManager.hasPlugin(androidAppPluginId) -> extensions.configure<BaseAppModuleExtension> {
            block(this)
        }

        pluginManager.hasPlugin(androidLibPluginId) -> extensions.configure<LibraryExtension> {
            block(this)
        }
    }
}