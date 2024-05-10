plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myweatherapp"
    compileSdk = 34

    ////TODO what is view binding, why have you enabled this please explain
   viewBinding{
      enable = true;
   }

    defaultConfig {
        applicationId = "com.example.myweatherapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    //TODO some of the dependencies are using a versions.toml file and another one is directly imported
    // Please fix this
    implementation("com.airbnb.android:lottie:6.4.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // GSON converter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
// retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

}