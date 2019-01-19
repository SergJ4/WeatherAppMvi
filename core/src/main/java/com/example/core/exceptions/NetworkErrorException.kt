package com.example.core.exceptions

const val NO_NETWORK_CONNECTION = 1
const val NOT_FOUND = 404

class NetworkErrorException(val code: Int = NO_NETWORK_CONNECTION) : Throwable()