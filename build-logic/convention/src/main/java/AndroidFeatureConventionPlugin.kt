import com.teampatch.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        dependencies {
            "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
            "implementation"(libs.findLibrary("androidx.navigation.compose").get())
            "implementation"(libs.findLibrary("hilt.navigation.compose").get())
        }
    }
}
