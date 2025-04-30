plugins {
    id("teampatch.android.library")
    id("teampatch.android.library.compose")
    id("teampatch.android.hilt")
    id("teampatch.android.feature")
}

android {
    namespace = "com.teampatch.feature.daily.edit"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}