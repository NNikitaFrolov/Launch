plugins {
    alias(libs.plugins.launch.android.library)
    alias(libs.plugins.launch.android.library.compose)
    alias(libs.plugins.launch.koin)
}

android {
    namespace = "nikitafrolov.launchapp"
}

dependencies {

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.jakewharton.timber)

    implementation(projects.root.core.model)
    implementation(projects.root.core.designsystem)
    implementation(projects.root.core.network)

    implementation(projects.root.feature.exchanger)
}