package com.example.repository.di

import com.example.core.interfaces.navigation.Router
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import javax.inject.Singleton

@Module
class RepoModule {

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