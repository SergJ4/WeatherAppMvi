package com.example.repository.datasource.api

import com.example.repository.model.api.TranslationResult
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslatorApi {

    //https://tech.yandex.ru/translate/doc/dg/reference/translate-docpage/
    @GET("translate")
    fun translate(
        @Query("key") apiKey: String = "trnsl.1.1.20181022T023123Z.855e00d4dc16c592.1ddb848f9b8fcbccc42aac3e101d4c1537b4b9f4",
        @Query("text") textToTranslate: String,
        @Query("lang") translateTo: String = "en"
    ): Single<TranslationResult>
}