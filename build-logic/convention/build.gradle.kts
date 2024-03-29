import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "nikitafrolov.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "launch.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "launch.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "launch.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeature") {
            id = "launch.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidDetekt") {
            id = "launch.android.detekt"
            implementationClass = "AndroidDetektConventionPlugin"
        }
        register("jvmLibrary") {
            id = "launch.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("koin") {
            id = "launch.koin"
            implementationClass = "KoinConventionPlugin"
        }
        register("kotlinSerialization") {
            id = "launch.kotlin.serialization"
            implementationClass = "KotlinSerializationPlugin"
        }
    }
}