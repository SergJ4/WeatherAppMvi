package com.example.weathersingle.di

import com.example.core.di.scopes.FragmentScope
import com.example.weathersingle.DetailsFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = [DetailsFragmentModule::class])
@FragmentScope
interface DetailsFragmentSubcomponent : AndroidInjector<DetailsFragment> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<DetailsFragment>() {

        abstract fun detailsModule(listFragmentModule: DetailsFragmentModule): Builder

        override fun seedInstance(instance: DetailsFragment) {
            detailsModule(DetailsFragmentModule(instance))
        }
    }
}