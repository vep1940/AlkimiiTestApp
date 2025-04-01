import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.vep1940.convention.androidModuleCommonConfiguration
import com.vep1940.convention.getPluginId
import com.vep1940.convention.getVersion
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply(libs.getPluginId("android-application"))
                apply(libs.getPluginId("kotlin-android"))
            }

            extensions.configure<BaseAppModuleExtension> {

                defaultConfig {

                    targetSdk = libs.getVersion("targetSdk").toInt()

                    versionCode = libs.getVersion("versionCode").toInt()
                    versionName = libs.getVersion("versionName")
                }

                androidModuleCommonConfiguration(this)
            }

            dependencies {

                add("implementation", libs.findLibrary("splash-screen").get())
            }
        }
    }
}