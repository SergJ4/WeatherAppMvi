package com.example.serg.mvicoretest.di

import com.example.weather.ListFragment
import com.example.weather.di.ListFragmentSubcomponent
import com.example.weathersingle.DetailsFragment
import com.example.weathersingle.di.DetailsFragmentSubcomponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module(subcomponents = [ListFragmentSubcomponent::class, DetailsFragmentSubcomponent::class])
internal abstract class FragmentModule {

    @Binds
    @IntoMap
    @ClassKey(ListFragment::class)
    internal abstract fun bindListFragmentInjectorFactory(
        builder: ListFragmentSubcomponent.Builder
    ): AndroidInjector.Factory<*>

    @Binds
    @IntoMap
    @ClassKey(DetailsFragment::class)
    internal abstract fun bindDetailsFragmentInjectorFactory(
        builder: DetailsFragmentSubcomponent.Builder
    ): AndroidInjector.Factory<*>
}