plugins {
    id("com.android.application")
}

android {
    namespace = "com.peoples.lifesaver"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.peoples.lifesaver"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.navigation:navigation-fragment:2.7.3")
    implementation("androidx.navigation:navigation-ui:2.7.3")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // Google Maps SDK
    implementation("com.google.android.gms:play-services-maps:18.2.0")

    // Maps Utils (for Heatmaps, clustering, etc.)
    implementation("com.google.maps.android:android-maps-utils:3.8.2")

    // Retrofit + Gson converter
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // AndroidX + Material
    implementation("androidx.core:core:1.12.0")   // 👈 use this instead of core-ktx for pure Java
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")

    // Google Play Services Location (for geofencing, RiskGeofenceReceiver, etc.)
    implementation("com.google.android.gms:play-services-location:21.0.1")
}
