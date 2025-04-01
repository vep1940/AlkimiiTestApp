import com.vep1940.convention.getLibrary
import com.vep1940.convention.getPluginId
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class GraphQlConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            pluginManager.apply {
                apply(libs.getPluginId("graphql-apollo"))
            }

            dependencies {
                add("implementation", libs.getLibrary("apollo-runtime"))
                add("implementation", libs.getLibrary("apollo-cache"))
            }
        }
    }
}