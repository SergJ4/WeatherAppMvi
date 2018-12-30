package com.example.core.di

import com.example.core.interfaces.navigation.Router
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    fun router(): Router

    fun navigationHolder(): NavigatorHolder
}