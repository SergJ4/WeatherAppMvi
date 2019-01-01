package com.example.core.di

import com.example.core.BuildConfig
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

@Component
@Singleton
interface CoreComponent {

    companion object {
        fun init(): CoreComponent {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
            return DaggerCoreComponent.create()
        }
    }
}