plugins {
    id("teampatch.android.library")
    id("teampatch.android.library.compose")
    id("teampatch.android.hilt")
}

android {
    namespace = "com.teampatch.feature.harmony.home"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.hilt.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}