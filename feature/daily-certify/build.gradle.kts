plugins {
    id("teampatch.android.library")
    id("teampatch.android.library.compose")
    id("teampatch.android.hilt")
    id("teampatch.android.feature")
}

android {
    namespace = "com.teampatch.feature.daily.certify"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))

    implementation("io.coil-kt:coil-compose:2.7.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}