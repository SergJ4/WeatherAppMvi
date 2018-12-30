package com.example.serg.mvicoretest.di

import com.example.core.di.ScopedComponent
import com.example.core.interfaces.IApplication
import com.example.repository.di.DaggerRepoComponent

class AppScopedComponent(private val appContext: IApplication) : ScopedComponent<AppComponent>() {

    override fun create(): AppComponent = DaggerAppComponent
        .builder()
        .app(appContext)
        .repoComponent(DaggerRepoComponent.create())
        .build()
}