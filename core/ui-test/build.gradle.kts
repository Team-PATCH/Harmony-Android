plugins {
    id("teampatch.android.library")
    id("teampatch.android.hilt")
}

android {
    namespace = "com.harmony.core.ui.test"
}

dependencies {

    implementation(libs.androidx.core.ktx)

    implementation(libs.junit)
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.hilt.android.test)
}