
plugins {
    alias(libs.plugins.launch.android.library)
    alias(libs.plugins.launch.koin)
    alias(libs.plugins.launch.kotlin.serialization)
}

android {
    namespace = "nikitafrolov.core.network"
}

dependencies {
    implementation(projects.root.core.model)

    implementation(libs.jakewharton.timber)

    api(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
}