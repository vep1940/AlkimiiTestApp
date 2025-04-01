import com.android.build.api.dsl.CommonExtension
import com.vep1940.convention.configureForAndroidModule
import com.vep1940.convention.getLibrary
import com.vep1940.convention.getPluginId
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            configureForAndroidModule { extension ->
                configureComposeOptions(extension)
            }

            dependencies {

                val bom = libs.getLibrary("compose-bom")

                add("implementation", platform(bom))
                add("implementation", libs.getLibrary("activity-compose"))
                add("implementation", libs.getLibrary("compose-material"))
                add("implementation", libs.getLibrary("compose-ui"))
                add("implementation", libs.getLibrary("compose-ui-graphics"))
                add("implementation", libs.getLibrary("compose-ui-tooling-preview"))
                add("implementation", libs.getLibrary("lifecycle-runtime-compose"))
                add("implementation", libs.getLibrary("navigation-compose"))

                add("debugImplementation", libs.getLibrary("compose-ui-tooling"))
                add("debugImplementation", libs.getLibrary("compose-ui-test-manifest"))

                add("androidTestImplementation", platform(bom))
                add("androidTestImplementation", libs.getLibrary("compose-ui-test-junit4"))
            }
        }
    }

    private fun Project.configureComposeOptions(commonExtension: CommonExtension<*, *, *, *, *, *>) {

        pluginManager.apply(libs.getPluginId("compose-compiler"))

        with(commonExtension) {
            buildFeatures {
                compose = true
            }
        }
    }
}