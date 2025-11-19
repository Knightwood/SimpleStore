plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    compileSdk = 34
    namespace = "com.kiylx.simplestore"
    defaultConfig {
        applicationId = "com.kiylx.simplestore"
        minSdk =23
        targetSdk =34
        versionCode= 1
        versionName= "1.0"

        //        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

}

dependencies {

    implementation ("androidx.core:core-ktx:1.17.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.13.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation(project(":store_lib"))
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

}
