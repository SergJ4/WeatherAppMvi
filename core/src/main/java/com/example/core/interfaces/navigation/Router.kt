package com.example.core.interfaces.navigation

const val WEATHER_LIST_SCREEN = "weather_list"
const val WEATHER_DETAILS_SCREEN = "weather_details"

interface Router {

    fun goTo(screen: Screen)

    fun rootScreen(screen: Screen)

    fun back()

    fun installModule(module: NavigationModule)

    fun uninstallModule(module: NavigationModule)
}