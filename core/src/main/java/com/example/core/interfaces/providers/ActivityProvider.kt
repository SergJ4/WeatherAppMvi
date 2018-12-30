package com.example.core.interfaces.providers

import com.example.core.interfaces.IApplication

interface ActivityProvider {
    fun getApplicationContext(): IApplication
}