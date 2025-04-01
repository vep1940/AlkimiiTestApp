import com.vep1940.convention.getLibrary
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType

class UnitTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {

            tasks.withType<Test> {
                useJUnitPlatform()
            }

            dependencies {

                add("testImplementation", libs.getLibrary("coroutine-test"))

                add("testImplementation", libs.getLibrary("junit5-api"))
                add("testImplementation", libs.getLibrary("junit5-params"))
                add("testRuntimeOnly", libs.getLibrary("junit5-engine"))

                add("testImplementation", libs.getLibrary("mockk"))

                add("testImplementation", libs.getLibrary("turbine"))
            }
        }
    }
}