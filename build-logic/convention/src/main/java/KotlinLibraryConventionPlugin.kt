import com.vep1940.convention.getJavaVersion
import com.vep1940.convention.getLibrary
import com.vep1940.convention.getPluginId
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply(libs.getPluginId("java-library"))
                apply(libs.getPluginId("kotlin-jvm"))
            }

            extensions.configure<JavaPluginExtension> {

                sourceCompatibility = getJavaVersion()
                targetCompatibility = getJavaVersion()

            }

            dependencies {
                add("implementation", libs.getLibrary("coroutine-core"))

                add("implementation", libs.getLibrary("koin-core"))
                add("testImplementation", libs.getLibrary("koin-test"))
            }
        }
    }
}