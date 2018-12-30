package com.example.core.di

object CoreComponentObject {

    val component = DaggerCoreComponent
        .builder()
        .build()
}