plugins {
    alias(libs.plugins.launch.android.library)
    alias(libs.plugins.launch.android.library.compose)
}

android {
    namespace = "nikitafrolov.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.constraintlayout)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)
}