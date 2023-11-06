plugins {
    id("com.android.application")
}

android {
    signingConfigs {
        create("sign1") {
            storeFile = file("/Users/tfh/Desktop/cs5520 keystore/classkeystore")
            storePassword = "6266207"
            keyAlias = "key1"
            keyPassword = "6266207"
        }
    }
    namespace = "edu.northeastern.numad23fa_theresahsu"
    compileSdk = 33

    defaultConfig {
        applicationId = "edu.northeastern.numad23fa_theresahsu"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("sign1")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}