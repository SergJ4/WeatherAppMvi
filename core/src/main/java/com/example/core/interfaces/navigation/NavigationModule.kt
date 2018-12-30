package com.example.core.interfaces.navigation

import androidx.fragment.app.Fragment

/**
 * Implementation of this interface will be installed into [Router] in order to handle
 * navigation between screens
 */
interface NavigationModule {

    fun isScreenNameSupported(screenName: String): Boolean

    fun getFragment(screenName: String, data: Any?): Fragment
}