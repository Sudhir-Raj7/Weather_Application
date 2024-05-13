// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
//// TODO what is the meaning of alias?
//// TODO why are you not using the android application plugin directly here?
//// TODO How is it referring the toml file?
    alias(libs.plugins.android.application) apply false
}