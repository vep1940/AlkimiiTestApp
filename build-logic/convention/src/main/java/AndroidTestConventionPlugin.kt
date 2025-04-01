import com.android.build.api.dsl.CommonExtension
import com.vep1940.convention.configureForAndroidModule
import com.vep1940.convention.getLibrary
import com.vep1940.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            configureForAndroidModule { extension ->
                configureTestInstrumentationRunner(extension)
            }

            dependencies {
                add("androidTestImplementation", libs.getLibrary("test-junit-ktx"))
            }
        }
    }

    private fun configureTestInstrumentationRunner(commonExtension: CommonExtension<*, *, *, *, *, *>) {
        commonExtension.defaultConfig {
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}