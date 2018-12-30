package com.example.repository.di

import com.example.core.interfaces.navigation.Router
import dagger.Component
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton

@Singleton
@Component(modules = [RepoModule::class])
interface RepoComponent {

    fun router(): Router

    fun navigationHolder(): NavigatorHolder
}