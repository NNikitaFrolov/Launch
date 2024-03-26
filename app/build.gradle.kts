import nikitafrolov.convention.LaunchBuildType
import java.util.Properties

plugins {
    alias(libs.plugins.launch.android.application)
    alias(libs.plugins.google.services)
}

android {
    namespace = "nikitafrolov.launch"
    val keyStorePropertiesFilePath = "../cert/keystore.properties"
    val keyStoreFilePath = "../cert/keystore.jks"

    defaultConfig {
        applicationId = "nikitafrolov.launch"
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        signingConfig = signingConfigs.getByName(LaunchBuildType.DEBUG.value)
    }

    signingConfigs {
        create(LaunchBuildType.RELEASE.value) {
            val keyStorePropertiesFile = file(keyStorePropertiesFilePath)
            if (keyStorePropertiesFile.exists()) {
                val keyStoreProperties = Properties().apply {
                    load(keyStorePropertiesFile.inputStream())
                }

                storeFile = file(keyStoreFilePath)
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
            if (file(keyStorePropertiesFilePath).exists()) {
                signingConfig = signingConfigs.findByName(LaunchBuildType.RELEASE.value)
            }
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