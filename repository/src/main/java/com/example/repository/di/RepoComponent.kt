package com.example.repository.di

import android.content.Context
import com.example.core.di.CoreComponent
import com.example.core.di.scopes.RepoScope
import com.example.core.interfaces.*
import dagger.BindsInstance
import dagger.Component

@RepoScope
@Component(
    modules = [RepoModule::class],
    dependencies = [CoreComponent::class]
)
interface RepoComponent {

    fun weatherRepository(): WeatherRepository

    fun logger(): Logger

    fun imageLoader(): ImageLoader

    fun translator(): Translator

    fun apiErrors(): ApiErrors

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): RepoComponent
    }
}