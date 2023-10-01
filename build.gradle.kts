// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("androidx.navigation.safeargs") version "2.7.3" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.1" apply false
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false

}
