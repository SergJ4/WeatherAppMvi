package com.example.weathersingle.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.ImageLoader
import com.example.core.interfaces.Strings
import com.example.weathersingle.CITY_ID_ARG
import com.example.weathersingle.DetailsFragment
import com.example.weathersingle.DetailsFragmentFeature
import com.example.weathersingle.DetailsFragmentViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DetailsFragmentModule(private val detailsFragment: DetailsFragment) {

    @Provides
    @FragmentScope
    fun provideViewModel(
        detailsFragmentFeature: DetailsFragmentFeature,
        imageLoader: ImageLoader,
        strings: Strings
    ): DetailsFragmentViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DetailsFragmentViewModel(
                    detailsFragmentFeature,
                    imageLoader,
                    strings
                ) as T
        }
        return ViewModelProviders.of(detailsFragment, factory)
            .get(DetailsFragmentViewModel::class.java)
    }

    @Provides
    @FragmentScope
    @Named(CITY_ID_ARG)
    fun provideCityId(): Long = detailsFragment.cityId
}