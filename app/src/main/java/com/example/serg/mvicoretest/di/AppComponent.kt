package com.example.serg.mvicoretest.di

import com.example.core.di.scopes.AppScope
import com.example.core.interfaces.IApplication
import com.example.core.interfaces.providers.ApplicationProvider
import com.example.repository.di.DaggerRepoComponent
import com.example.repository.di.RepoComponent
import com.example.serg.mvicoretest.App
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(
    modules = [AppModule::class],
    dependencies = [RepoComponent::class]
)
interface AppComponent : ApplicationProvider {
    fun inject(app: App)

    fun mainActivity(): MainActivityComponent.Builder

    companion object {
        fun init(app: IApplication): AppComponent {
            val repoComponent = DaggerRepoComponent
                .builder()
                .build()

            return DaggerAppComponent
                .builder()
                .app(app)
                .repoComponent(repoComponent)
                .build()
        }
    }

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun app(context: IApplication): Builder

        fun repoComponent(repoComponent: RepoComponent): Builder

        fun build(): AppComponent
    }
}