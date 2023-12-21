pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Launch"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":root:app")
include(":root:core:model")
include(":root:core:designsystem")
include(":root:core:network")
include(":root:feature:exchanger")
