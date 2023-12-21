import nikitafrolov.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KotlinSerializationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                "implementation"(libs.findLibrary("kotlinx.serialization").get())
            }

        }
    }

}
