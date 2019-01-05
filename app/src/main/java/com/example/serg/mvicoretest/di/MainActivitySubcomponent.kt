package com.example.serg.mvicoretest.di

import com.example.core.di.scopes.ActivityScope
import com.example.serg.mvicoretest.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [MainActivityModule::class, FragmentModule::class])
@ActivityScope
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>() {

        abstract fun mainActivityModule(mainActivityModule: MainActivityModule): Builder

        override fun seedInstance(instance: MainActivity) {
            mainActivityModule(
                MainActivityModule(
                    instance.containerId,
                    instance.supportFragmentManager,
                    instance
                )
            )
        }
    }
}