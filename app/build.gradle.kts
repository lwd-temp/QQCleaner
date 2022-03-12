plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    compileSdk = 32
    buildToolsVersion = "32.0.0"

    defaultConfig {
        applicationId = "me.kyuubiran.qqcleaner"
        minSdk = 26
        targetSdk = 32
        versionCode = 69
        versionName = "2.0.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        named("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            setProguardFiles(listOf("proguard-rules.pro"))
            signingConfig = signingConfigs.getByName("debug") {
                enableV3Signing = true
                enableV4Signing = true
            }
        }
    }

    buildFeatures {
        compose = true
    }

    val composeVersion: String by project
    composeOptions {
        kotlinCompilerExtensionVersion = composeVersion
    }

    androidResources {
        additionalParameters("--preferred-density", "xxxhdpi")
        additionalParameters("--allow-reserved-package-id", "--package-id", "0x63")
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        resources.excludes.addAll(listOf("META-INF/**", "kotlin/**", "google/**", "**.bin"))
    }

    dependenciesInfo {
        includeInApk = false
    }
    namespace = "me.kyuubiran.qqcleaner"
}

dependencies {
    val composeVersion: String by project

//    implementation(files("./libs/EzXHelper-release.aar"))

    implementation("com.github.kyuubiran:EzXHelper:0.7.1")
    compileOnly("de.robv.android.xposed:api:82")

    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    // 导航
    implementation("androidx.navigation:navigation-compose:2.5.0-alpha03")
    // 虚拟键之类的适配工具
    implementation("com.google.accompanist:accompanist-insets:0.24.3-alpha")
    // 为了按钮添加的支持库
    implementation("androidx.compose.animation:animation-graphics:1.2.0-alpha04")
}
