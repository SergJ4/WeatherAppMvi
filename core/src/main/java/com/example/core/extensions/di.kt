package com.example.core.extensions

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.core.interfaces.IApplication
import com.example.core.interfaces.providers.ApplicationProvider

fun Fragment?.findDependencies(): ApplicationProvider {
    if (this == null) {
        throw Exception("FragmentActivity mustn't be null")
    }

    return (this.activity?.applicationContext as IApplication).getAppComponent()
}

fun Activity?.findDependencies(): ApplicationProvider {
    if (this == null) {
        throw Exception("Activity mustn't be null")
    }

    return (this.applicationContext as IApplication).getAppComponent()
}