package com.example.core.interfaces


interface Logger {

    fun logErrorIfDebug(error: Throwable)

    fun logDebug(message: String, tag: String)
}