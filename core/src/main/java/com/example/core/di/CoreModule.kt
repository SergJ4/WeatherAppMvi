package com.example.core.di

import com.example.core.implementations.RouterImpl
import com.example.core.interfaces.navigation.Router
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import javax.inject.Singleton

@Module
class CoreModule {

    @Provides
    @Singleton
    fun provideCicerone() = Cicerone.create()

    @Provides
    @Singleton
    fun provideCiceroneRouter(cicerone: Cicerone<ru.terrakok.cicerone.Router>) =
        cicerone.router

    @Provides
    @Singleton
    fun provideNavigationHolder(cicerone: Cicerone<ru.terrakok.cicerone.Router>) =
        cicerone.navigatorHolder

    @Provides
    @Singleton
    fun provideRouter(ciceroneRouter: ru.terrakok.cicerone.Router): Router {
        return RouterImpl(ciceroneRouter)
    }
}