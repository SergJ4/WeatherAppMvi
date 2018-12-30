package com.example.serg.mvicoretest.di

import androidx.fragment.app.FragmentManager
import com.example.core.di.scopes.ActivityScope
import com.example.serg.mvicoretest.AppNavigator
import com.example.serg.mvicoretest.MainActivity
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Navigator

@Module
class MainActivityModule {

    @Provides
    @ActivityScope
    fun provideNavigator(
        fragmentContainerId: Int,
        fm: FragmentManager,
        activity: MainActivity
    ): Navigator =
        AppNavigator(activity, fm, fragmentContainerId)
}