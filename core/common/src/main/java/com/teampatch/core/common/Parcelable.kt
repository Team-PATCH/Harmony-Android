package com.teampatch.core.common

import android.content.Intent

fun <T> Intent.getCustomParcelableExtra(name: String, clazz: Class<T>): T? {
    if (android.os.Build.VERSION.SDK_INT >= 33) {
        return getParcelableExtra(name, clazz)
    }
    return getParcelableExtra(name)
}