plugins {
    id("teampatch.android.library")
    id("teampatch.android.library.compose")
    id("teampatch.android.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.teampatch.core.authentication"

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
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