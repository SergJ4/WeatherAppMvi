package com.example.core.interfaces

import android.content.Context
import com.example.core.interfaces.providers.ApplicationProvider

interface IApplication {
    fun getAppComponent(): ApplicationProvider
    fun getApplicationContext(): Context
}