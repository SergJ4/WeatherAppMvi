package com.example.serg.mvicoretest

import android.content.Context
import com.example.core.di.scopes.AppScope
import com.example.core.extensions.color
import com.example.core.interfaces.Colors
import javax.inject.Inject

@AppScope
class ColorsImpl @Inject constructor(private val appContext: Context) : Colors {

    override fun get(colorInt: Int): Int = appContext.color(colorInt)
}