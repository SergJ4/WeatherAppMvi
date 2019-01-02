package com.example.weather.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.FragmentScope
import com.example.weather.ListFragment
import com.example.weather.ListFragmentFeature
import com.example.weather.ListFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class ListFragmentModule(private val listFragment: ListFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(listFragmentFeature: ListFragmentFeature): ListFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ListFragmentViewModel(listFragmentFeature) as T
        }
        return ViewModelProviders.of(listFragment, factory).get(ListFragmentViewModel::class.java)
    }
}