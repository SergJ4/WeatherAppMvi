package com.example.serg.mvicoretest

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.core.interfaces.Router
import com.example.core.interfaces.WEATHER_LIST_SCREEN

class MainActivityViewModel(private val router: Router) : ViewModel() {

    fun handleNavigation(savedState: Bundle?) {
        if (savedState == null) {
            router.rootScreen(WEATHER_LIST_SCREEN)
        }
    }
}