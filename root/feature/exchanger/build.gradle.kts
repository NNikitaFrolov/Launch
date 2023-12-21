plugins {
    alias(libs.plugins.launch.android.feature)
    alias(libs.plugins.launch.android.library.compose)
    alias(libs.plugins.launch.koin)
}

android {
    namespace = "nikitafrolov.feature.exchanger"
}

dependencies {
    implementation(projects.root.core.network)

    implementation(libs.kotlinx.datetime)
}