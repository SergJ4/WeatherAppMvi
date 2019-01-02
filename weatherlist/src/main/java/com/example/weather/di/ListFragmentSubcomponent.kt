package com.example.weather.di

import com.example.core.di.scopes.FragmentScope
import com.example.weather.ListFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [ListFragmentModule::class])
@FragmentScope
interface ListFragmentSubcomponent : AndroidInjector<ListFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<ListFragment>() {

        abstract fun listModule(listFragmentModule: ListFragmentModule): Builder

        override fun seedInstance(instance: ListFragment) {
            listModule(ListFragmentModule(instance))
        }
    }
}