package com.example.weathersingle.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Colors
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.Router
import com.example.core.interfaces.Strings
import com.example.weathersingle.DetailsFragment
import com.example.weathersingle.DetailsFragmentViewModel
import dagger.Module
import dagger.Provides

@Module
class DetailsFragmentModule(private val detailsFragment: DetailsFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(
        imageLoader: ImageLoader,
        router: Router,
        colors: Colors,
        strings: Strings
    ): DetailsFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DetailsFragmentViewModel(
                    router
                ) as T
        }
        return ViewModelProviders.of(detailsFragment, factory)
            .get(DetailsFragmentViewModel::class.java)
    }
}