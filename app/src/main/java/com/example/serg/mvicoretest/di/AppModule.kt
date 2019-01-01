package com.example.serg.mvicoretest.di

import com.example.core.di.scopes.AppScope
import com.example.serg.mvicoretest.RouterImpl
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideCicerone() = Cicerone.create()

    @Provides
    @AppScope
    fun provideCiceroneRouter(cicerone: Cicerone<Router>) =
        cicerone.router

    @Provides
    @AppScope
    fun provideNavigationHolder(cicerone: Cicerone<Router>) =
        cicerone.navigatorHolder

    @Provides
    @AppScope
    fun provideRouter(ciceroneRouter: ru.terrakok.cicerone.Router): com.example.core.interfaces.Router {
        return RouterImpl(ciceroneRouter)
    }
}