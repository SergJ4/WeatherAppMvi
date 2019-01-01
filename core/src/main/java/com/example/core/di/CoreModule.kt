package com.example.core.di

import com.example.core.implementation.LoggerImpl
import com.example.core.interfaces.Logger
import dagger.Binds
import dagger.Module

@Module
abstract class CoreModule {

    @Binds
    abstract fun logger(logger: LoggerImpl): Logger
}