plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint")
    id("io.gitlab.arturbosch.detekt")
}

android {
    namespace = "io.github.ytam.rickandmorty"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.ytam.rickandmorty"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ksp {
            arg("room.expandProjection", "true")
            arg("room.incremental", "true")
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildFeatures {
        viewBinding = true
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
    kotlinOptions {
        jvmTarget = "17"
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-splashscreen:1.0.1")

    // Navigation Component
    val vNavigation = "2.7.3"
    implementation("androidx.navigation:navigation-fragment-ktx:$vNavigation")
    implementation("androidx.navigation:navigation-ui-ktx:$vNavigation")
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$vNavigation")

    // Retrofit
    val vRetrofit = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$vRetrofit")
    implementation("com.squareup.retrofit2:converter-gson:$vRetrofit")
    implementation("com.squareup.retrofit2:adapter-rxjava2:$vRetrofit")

    // Room
    val vRoom = "2.5.2"
    implementation("androidx.room:room-runtime:$vRoom")
    annotationProcessor("androidx.room:room-compiler:$vRoom")
    ksp("androidx.room:room-compiler:$vRoom")
    implementation("androidx.room:room-ktx:$vRoom")
    implementation("androidx.room:room-paging:$vRoom")
    implementation("androidx.room:room-rxjava2:$vRoom")

    // Okhttp
    val vOkhttp = "4.10.0"
    implementation("com.squareup.okhttp3:okhttp:$vOkhttp")
    implementation("com.squareup.okhttp3:logging-interceptor:$vOkhttp")

    // Lifecycle
    implementation("android.arch.lifecycle:extensions:1.1.1")

    // Coil
    implementation("io.coil-kt:coil:2.2.2")

    // Coroutines
    val vCoroutines = "1.6.4"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$vCoroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$vCoroutines")

    // Hilt
    val vHilt = "2.48"
    implementation("com.google.dagger:hilt-android:$vHilt")
    kapt("com.google.dagger:hilt-android-compiler:$vHilt")
    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")

    // Timber
    implementation("com.jakewharton.timber:timber:5.0.1")

}
