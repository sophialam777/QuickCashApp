plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.iteration1"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.iteration1"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    packagingOptions {
        resources.excludes += setOf("META-INF/NOTICE.md", "META-INF/LICENSE.md")
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.firebase.database)

    implementation(libs.firebase.auth)
    implementation(libs.uiautomator)
    implementation(libs.play.services.maps)

    testImplementation("org.robolectric:robolectric:4.11")

    testImplementation("org.mockito:mockito-core:5.6.0")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Add JavaMail dependencies
    implementation(libs.android.mail)
    implementation(libs.android.activation)
    implementation(libs.espresso.intents)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.paypal.sdk:paypal-android-sdk:2.16.0")
}

