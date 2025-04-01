import com.android.build.api.dsl.LibraryExtension
import com.vep1940.convention.androidModuleCommonConfiguration
import com.vep1940.convention.getPluginId
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply(libs.getPluginId("android-library"))
                apply(libs.getPluginId("kotlin-android"))
            }

            extensions.configure<LibraryExtension> {

                androidModuleCommonConfiguration(this)

            }
        }
    }
}