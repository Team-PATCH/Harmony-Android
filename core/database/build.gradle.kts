plugins {
    id("teampatch.android.library")
}

android {
    namespace = "com.harmony.core.database"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}