
plugins {
    alias(libs.plugins.launch.android.library)
    alias(libs.plugins.launch.koin)
    alias(libs.plugins.launch.kotlin.serialization)
}

android {
    namespace = "nikitafrolov.core.network"
}

dependencies {
    api(libs.squareup.retrofit)

    implementation(libs.squareup.okHttp)
    implementation(libs.jakewharton.timber)
    implementation(libs.jakewharton.retrofit.converter)
}