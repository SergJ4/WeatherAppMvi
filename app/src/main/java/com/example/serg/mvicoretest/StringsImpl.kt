package com.example.serg.mvicoretest

import android.content.Context
import com.example.core.interfaces.Strings

class StringsImpl(private val appContext: Context) : Strings {
    override fun errorChoosingCityString(): String =
        appContext.getString(R.string.error_choosing_city)

    override fun unknownErrorString(): String = appContext.getString(R.string.unknown_error)

}