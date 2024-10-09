package com.teampatch.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.configureKotlin(
    extension: CommonExtension<*, *, *, *, *, *>,
) {
    extension.apply {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        kotlinExtension.apply {
            version = "17"
            jvmToolchain(17)
        }
    }
}