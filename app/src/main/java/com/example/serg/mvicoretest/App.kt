package com.example.serg.mvicoretest

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.serg.mvicoretest.di.AppInjector

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}