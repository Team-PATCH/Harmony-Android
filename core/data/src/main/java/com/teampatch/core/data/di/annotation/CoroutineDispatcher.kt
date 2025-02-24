package com.teampatch.core.data.di.annotation

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class CoroutineDispatcher(val dispatcherContext: DispatcherContext)

enum class DispatcherContext {
    Default, IO, Main
}