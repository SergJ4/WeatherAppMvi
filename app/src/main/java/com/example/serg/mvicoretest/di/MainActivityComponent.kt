package com.example.serg.mvicoretest.di

import com.example.core.di.scopes.ActivityScope
import com.example.serg.mvicoretest.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }
}