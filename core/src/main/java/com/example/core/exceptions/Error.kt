package com.example.core.exceptions

sealed class Error

object RefreshDataError : Error()

object NetworkConnectionError : Error()

object ResourceNotFoundError : Error()