import com.vep1940.convention.getLibrary
import com.vep1940.convention.getPluginId
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class SqlDelightConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply(libs.getPluginId("sql-delight"))
            }

            dependencies {
                add("implementation", libs.getLibrary("sqldelight-driver"))
                add("implementation", libs.getLibrary("sqldelight-coroutines-extensions"))
            }
        }
    }
}