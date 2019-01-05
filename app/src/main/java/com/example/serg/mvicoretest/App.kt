package com.example.serg.mvicoretest

import android.app.Activity
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.core.di.CoreComponent
import com.example.repository.di.DaggerRepoComponent
import com.example.serg.mvicoretest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : MultiDexApplication(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector


    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .app(this)
            .repoComponent(
                DaggerRepoComponent
                    .builder()
                    .appContext(this)
                    .coreComponent(CoreComponent.init())
                    .build()
            )
            .build()
            .inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}