package com.example.serg.mvicoretest.di

import android.content.Context
import com.example.core.di.CoreComponent
import com.example.core.di.scopes.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class]
)
interface AppComponent {
    fun mainActivityComponent(): MainActivityComponent.Builder

    fun context(): Context

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(context: Context): Builder

        fun coreComponent(coreComponent: CoreComponent): Builder

        fun build(): AppComponent
    }
}