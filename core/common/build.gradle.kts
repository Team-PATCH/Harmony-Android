plugins {
    id("teampatch.android.library")
}

android {
    namespace = "com.teampatch.core.common"
}

dependencies {
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
}