package com.example.repository.datasource.api

import com.example.core.interfaces.Translator
import io.reactivex.Single

class TranslatorImpl(private val translatorService: TranslatorApi) : Translator {

    override fun translate(
        textToTranslate: String,
        toLanguage: String
    ): Single<String> =
        translatorService.translate(
            textToTranslate = textToTranslate,
            translateTo = toLanguage
        ).map { it.text[0] }
}