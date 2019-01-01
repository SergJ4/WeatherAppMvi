package com.example.serg.mvicoretest.di

import androidx.fragment.app.FragmentManager
import com.example.core.di.scopes.ActivityScope
import com.example.serg.mvicoretest.AppNavigator
import com.example.serg.mvicoretest.MainActivity
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
    fun provideNavigator(): Navigator =
        AppNavigator(activity, fm, containerId)
}