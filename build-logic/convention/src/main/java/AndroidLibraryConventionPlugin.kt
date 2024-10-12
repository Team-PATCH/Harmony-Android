import com.teampatch.convention.configureKotlin
import com.teampatch.convention.libs
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlin(this)
                compileSdk = 34

                buildTypes {
                    release {
                        isMinifyEnabled = true
                    }
                }

                defaultConfig {
                    minSdk = 24

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    isCoreLibraryDesugaringEnabled = true
                }

                dependencies {
                    "implementation"(libs.findLibrary("androidx.core.ktx").get())
                    "coreLibraryDesugaring"(libs.findLibrary("android.tools.desugar").get())
                }
            }
        }
    }
}