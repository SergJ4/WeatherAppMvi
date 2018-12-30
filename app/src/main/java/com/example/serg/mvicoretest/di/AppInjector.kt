package com.example.serg.mvicoretest.di

import com.example.core.di.CoreComponentObject
import com.example.serg.mvicoretest.App

object AppInjector {

    lateinit var appComponent: AppComponent

    /**
     * Initialize dependencies graph and inject in application
     *
     * @param application application
     */
    fun init(application: App) {
        appComponent = DaggerAppComponent.builder()
            .appContext(application)
            .coreComponent(CoreComponentObject.component)
            .build()
    }

}