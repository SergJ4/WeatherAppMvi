package com.example.serg.mvicoretest.di

import com.example.serg.mvicoretest.MainActivity
import dagger.Subcomponent

@Subcomponent
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainActivityComponent
    }
}