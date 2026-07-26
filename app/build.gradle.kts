plugins {
    id("com.android.application")
}

android {
    namespace = "com.emroozchand.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.emroozchand.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0-demo"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
