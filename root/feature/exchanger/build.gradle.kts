plugins {
    alias(libs.plugins.launch.android.feature)
    alias(libs.plugins.launch.android.library.compose)
}

android {
    namespace = "nikitafrolov.feature.exchanger"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}