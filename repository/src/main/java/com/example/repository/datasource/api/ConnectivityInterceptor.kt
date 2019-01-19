package com.example.repository.datasource.api

import android.content.Context
import com.example.core.exceptions.NOT_FOUND
import com.example.core.exceptions.NetworkErrorException
import com.example.core.extensions.hasNetwork
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Перехватчик для определения наличия сетевого подключения на девайсе
 * и наличие ошибок диапазона 400-599 в ответе.
 * Все сгенерированные здесь исключения далее по цепочке попадают
 * в [ErrorsInterceptor]
 */
internal class ConnectivityInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //Проверяем наличие сети
        if (!context.hasNetwork()) {
            throw NetworkErrorException()
        }

        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())
        //Проверяем код ответа на наличие ошибок
        if ((response.code() >= 400) and (response.code() < 600)) {
            if (response.code() == 404) {
                throw NetworkErrorException(NOT_FOUND)
            } else {
                throw NetworkErrorException()
            }
        }
        return response
    }
}
