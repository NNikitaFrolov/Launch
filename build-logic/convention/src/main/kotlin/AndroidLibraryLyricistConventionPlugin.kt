import com.google.devtools.ksp.gradle.KspExtension
import nikitafrolov.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryLyricistConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("com.google.devtools.ksp")
            }

            with(extensions.getByType<KspExtension>()) {
                arg("lyricist.internalVisibility", "true")
                arg("lyricist.generateStringsProperty", "true")
            }

            dependencies {
                "implementation"(libs.findLibrary("lyricist").get())
                "ksp"(libs.findLibrary("lyricist-processor").get())
            }
        }
    }
}