plugins {
    alias(libs.plugins.launch.android.feature)
    alias(libs.plugins.launch.android.library.compose)
    alias(libs.plugins.launch.koin)
    alias(libs.plugins.launch.kotlin.serialization)
    alias(libs.plugins.launch.android.library.lyricist)
}

android {
    namespace = "nikitafrolov.feature.exchanger"
}

ksp {
    arg("lyricist.packageName", "nikitafrolov.feature.exchanger")
    arg("lyricist.moduleName", "exchanger")
}

dependencies {
    implementation(projects.root.core.network)

    implementation(libs.kotlinx.datetime)
}