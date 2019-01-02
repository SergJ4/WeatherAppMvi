package com.example.weather

import androidx.lifecycle.ViewModel

class ListFragmentViewModel(private val listFeature: ListFragmentFeature) : ViewModel() {

    init {
        listFeature.accept(ListFragmentFeature.Wish.Refresh)
    }
}