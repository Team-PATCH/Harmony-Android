plugins {
    id("teampatch.android.library")
    id("teampatch.android.hilt")
}

android {
    namespace = "com.teampatch.core.data"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:preferences"))
    implementation(project(":core:network"))

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}