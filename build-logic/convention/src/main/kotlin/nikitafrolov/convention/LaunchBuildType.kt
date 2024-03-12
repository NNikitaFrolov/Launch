package nikitafrolov.convention

enum class LaunchBuildType(val value: String, val applicationIdSuffix: String? = null) {
    DEBUG("debug", ".debug"),
    RELEASE("release")
}
