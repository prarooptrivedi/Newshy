plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.praroop.newshy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.praroop.newshy"
        minSdk = 24
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //compose
    val navVersion = "2.8.9"
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.material.icons.extended)
    implementation (libs.androidx.ui.util)
    implementation(libs.androidx.lifecycle.viewmodel.compose.v262)
    implementation(libs.androidx.lifecycle.runtime.compose)
    //Paging3
    val paging_version = "3.3.6"
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime.ktx)


    //Coil
    implementation(libs.coil.compose)

    // Network
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0")
    implementation(libs.retrofit.core)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor.v4120)
    implementation(libs.kotlinx.serialization.json)


    // implementation (libs.kotlinx.serialization.json)
    //splash
    implementation (libs.androidx.core.splashscreen)

    // dagger Hilt
    // Hilt for Dependency Injection
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.compose)


    val roomVersion = "2.7.1"
    implementation(libs.androidx.room.runtime.v271)
    ksp(libs.androidx.room.compiler.v271)
    implementation(libs.androidx.room.ktx.v271)
   implementation(libs.androidx.room.paging)
}
