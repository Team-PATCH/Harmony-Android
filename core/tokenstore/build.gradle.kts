plugins {
    id("teampatch.android.library")
    id("teampatch.android.hilt")
}

android {
    namespace = "com.teampatch.core.tokenstore"
}

dependencies {

    implementation(libs.androidx.security.crypto)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}