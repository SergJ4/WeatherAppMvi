package com.example.core.exceptions

data class SimpleMessageException(val errorId: Int = 0, val exceptionMessage: String = "") :
    Throwable()
