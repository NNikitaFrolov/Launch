import nikitafrolov.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                val koinBom = libs.findLibrary("koin-bom").get()
                add("implementation", platform(koinBom))
                "implementation"(libs.findLibrary("koin.android").get())
                "implementation"(libs.findLibrary("koin.annotations").get())
                "ksp"(libs.findLibrary("koin.ksp.compiler").get())
            }

        }
    }

}
