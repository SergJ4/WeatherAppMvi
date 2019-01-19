package com.example.serg.mvicoretest.di

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.core.di.scopes.ActivityScope
import com.example.core.interfaces.Router
import com.example.serg.mvicoretest.AppNavigator
import com.example.serg.mvicoretest.MainActivity
import com.example.serg.mvicoretest.MainActivityViewModel
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator

@Module
class MainActivityModule(
    private val containerId: Int,
    private val fm: FragmentManager,
    private val activity: MainActivity
) {

    @Provides
    @ActivityScope
    fun provideNavigator(): Navigator = AppNavigator(activity, fm, containerId)

    @Provides
    @ActivityScope
    fun viewModel(router: Router): MainActivityViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                MainActivityViewModel(router) as T
        }
        return ViewModelProviders.of(activity, factory).get(MainActivityViewModel::class.java)
    }
}