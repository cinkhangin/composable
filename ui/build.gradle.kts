@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.vanniktech.maven)
}

kotlin {
    android {
        namespace = "com.ckgin.composable.ui"
        compileSdk = 37
        minSdk = 28

        androidResources {
            enable = true
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    jvm("desktop")

    iosArm64()
    iosSimulatorArm64()

    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)
            api(compose.components.resources)
            api("org.jetbrains.kotlinx:kotlinx-datetime:0.7.1")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

mavenPublishing {
    coordinates(
        groupId = "com.ckgin",
        artifactId = "composable-ui",
        version = "0.1.0"
    )

    publishToMavenCentral()
    signAllPublications()

    pom {
        name = "Composable UI"
        description = "Reusable Compose Multiplatform UI components for Android, iOS, desktop, and web."
        inceptionYear = "2025"
        url = "https://github.com/cinkhangin/composable"

        licenses {
            license {
                name = "MIT License"
                url = "https://opensource.org/license/mit"
                distribution = "https://opensource.org/license/mit"
            }
        }

        developers {
            developer {
                id = "cinkhangin"
                name = "Naulian"
                url = "https://github.com/cinkhangin"
            }
        }

        scm {
            url = "https://github.com/cinkhangin/composable"
            connection = "scm:git:git://github.com/cinkhangin/composable.git"
            developerConnection = "scm:git:ssh://git@github.com/cinkhangin/composable.git"
        }
    }
}
