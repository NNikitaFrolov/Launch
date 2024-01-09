import nikitafrolov.convention.LaunchBuildType

plugins {
    alias(libs.plugins.launch.android.application)
    alias(libs.plugins.google.services)
}

android {
    namespace = "nikitafrolov.launch"

    defaultConfig {
        applicationId = "nikitafrolov.launch"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = LaunchBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = LaunchBuildType.RELEASE.applicationIdSuffix
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(projects.root.app)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}