package com.example.weather.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Colors
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.Router
import com.example.core.interfaces.Strings
import com.example.weather.ListFragment
import com.example.weather.ListFragmentFeature
import com.example.weather.ListFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class ListFragmentModule(private val listFragment: ListFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(
        listFragmentFeature: ListFragmentFeature,
        imageLoader: ImageLoader,
        router: Router,
        colors: Colors,
        strings: Strings
    ): ListFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ListFragmentViewModel(
                    listFragmentFeature,
                    imageLoader,
                    router,
                    colors,
                    strings
                ) as T
        }
        return ViewModelProviders.of(listFragment, factory).get(ListFragmentViewModel::class.java)
    }

    @Provides
    @FragmentScope
    fun provideFragment(): Fragment = listFragment
}