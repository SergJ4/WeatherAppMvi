package com.example.weather.di

import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.FragmentScope
import com.example.weather.ListFragment
import com.example.weather.ListFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class ListFragmentModule(private val listFragment: ListFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel() =
        ViewModelProviders.of(listFragment).get(ListFragmentViewModel::class.java)
}