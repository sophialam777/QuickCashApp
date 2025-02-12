plugins {
    alias(libs.plugins.android.application)
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
}

dependencies {
    implementation(libs.firebase.database)
    testImplementation("org.robolectric:robolectric:4.9")
    testImplementation("org.robolectric:robolectric:4.8.2")

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
}