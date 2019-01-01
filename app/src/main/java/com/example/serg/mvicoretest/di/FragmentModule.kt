package com.example.serg.mvicoretest.di

import androidx.fragment.app.ListFragment
import com.example.weather.di.ListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class FragmentModule {

    @ContributesAndroidInjector(modules = [ListFragmentModule::class])
    abstract fun listFragment(): ListFragment
}