package com.example.serg.mvicoretest.di

import com.example.core.di.scopes.AppScope
import com.example.core.interfaces.IApplication
import com.example.repository.di.RepoComponent
import com.example.serg.mvicoretest.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@AppScope
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivityModule::class,
        AppModule::class
    ],
    dependencies = [RepoComponent::class]
)
interface AppComponent {
    fun inject(app: App)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(context: IApplication): Builder

        fun repoComponent(repoComponent: RepoComponent): Builder

        fun build(): AppComponent
    }
}