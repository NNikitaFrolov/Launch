plugins {
    alias(libs.plugins.launch.android.library)
    alias(libs.plugins.launch.android.library.compose)
    alias(libs.plugins.launch.android.library.lyricist)
}

android {
    namespace = "nikitafrolov.core.designsystem"
}

ksp {
    arg("lyricist.packageName", "nikitafrolov.core.designsystem")
    arg("lyricist.moduleName", "designsystem")
    arg("lyricist.internalVisibility", "false")
    arg("lyricist.generateStringsProperty", "false")
}

dependencies {
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.constraintlayout)

    implementation(projects.root.core.model)

    debugApi(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.core.ktx)
}