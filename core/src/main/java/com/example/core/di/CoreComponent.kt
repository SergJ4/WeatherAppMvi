package com.example.core.di

import com.example.core.BuildConfig
import com.example.core.interfaces.Logger
import dagger.Component
import timber.log.Timber
import javax.inject.Singleton

@Component(modules = [CoreModule::class])
@Singleton
interface CoreComponent {

    fun logger(): Logger

    companion object {
        fun init(): CoreComponent {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
            return DaggerCoreComponent.create()
        }
    }
}