plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.myweatherapp"
    compileSdk = 34

    ////TODO what is view binding, why have you enabled this please explain
    /*

View Binding is a feature that helps to interact with app's UI components in an efficient manner.
we dont have to use findViewById() which reduces the chances of runtime errors,
It also reduces the amount of code which is required to interact with the UI components leading to
cleaner and maintainable code.
It also saves us from NullPointerException because the binding class is generated based on the layout which
ensures that the references to the views are correct.

     */
   viewBinding{
      enable = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //TODO some of the dependencies are using a versions.toml file and another one is directly imported
    // Please fix this
    implementation(libs.lottie) // now uses versions.toml
    implementation(libs.retrofit) //now uses versions.toml
    implementation(libs.gsonConverter) //now uses versions.toml

}