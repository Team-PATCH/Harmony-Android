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
    implementation(project(":core:network"))
    implementation(project(":core:authentication"))
    implementation(project(":core:database"))
    implementation(project(":core:designsystem"))

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation(libs.google.play.app.update)

    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.security.crypto)
    implementation(project(":feature:daily-certify"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}