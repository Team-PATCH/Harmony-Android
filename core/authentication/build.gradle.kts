plugins {
    id("teampatch.android.library")
    id("teampatch.android.library.compose")
    id("teampatch.android.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.agvber.core.authentication"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))

    implementation(libs.kakao.sdk.v2.user)
    implementation(libs.androidx.activity.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}