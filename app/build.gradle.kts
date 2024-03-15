import nikitafrolov.convention.LaunchBuildType
import java.util.Properties

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
    }

    signingConfigs {
        if (rootProject.file("keystore.properties").exists()) {
            create(LaunchBuildType.RELEASE.value) {
                val keyStorePropertiesFile = file("../cert/keystore.properties")
                val keyStoreProperties =  Properties().apply {
                    load(keyStorePropertiesFile.inputStream())
                }

                storeFile = file("../cert/keystore.jks")
                storePassword = keyStoreProperties.getProperty("storePassword")
                keyAlias = keyStoreProperties.getProperty("keyAlias")
                keyPassword = keyStoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = LaunchBuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = true
            applicationIdSuffix = LaunchBuildType.RELEASE.applicationIdSuffix
            signingConfig = signingConfigs.findByName(LaunchBuildType.RELEASE.value)
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