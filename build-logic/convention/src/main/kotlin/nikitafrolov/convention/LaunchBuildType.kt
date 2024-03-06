package nikitafrolov.convention

enum class LaunchBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE
}
