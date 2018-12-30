package com.example.serg.mvicoretest

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.core.interfaces.IApplication
import com.example.core.interfaces.providers.ApplicationProvider
import com.example.serg.mvicoretest.di.AppComponent

class App : MultiDexApplication(), IApplication {

    private val appComponent: AppComponent by lazy { AppComponent.init(this@App) }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun getAppComponent(): ApplicationProvider = appComponent
}