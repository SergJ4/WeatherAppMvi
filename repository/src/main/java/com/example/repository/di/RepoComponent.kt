package com.example.repository.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepoModule::class])
interface RepoComponent {
}