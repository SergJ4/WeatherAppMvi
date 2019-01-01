package com.example.serg.mvicoretest

import androidx.fragment.app.Fragment
import com.example.core.interfaces.Router
import com.example.core.interfaces.WEATHER_DETAILS_SCREEN
import com.example.core.interfaces.WEATHER_LIST_SCREEN
import com.example.weather.ListFragment
import com.example.weathersingle.DetailsFragment
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen

internal class RouterImpl(private val router: ru.terrakok.cicerone.Router) :
    Router {

    override fun goTo(screenName: String, data: Any?) {
        router.navigateTo(convertNameToScreen(screenName, data))
    }

    override fun rootScreen(screenName: String, data: Any?) {
        router.newRootScreen(convertNameToScreen(screenName, data))
    }

    override fun back() = router.exit()

    private fun convertNameToScreen(screenName: String, data: Any?): Screen =
        when (screenName) {
            WEATHER_LIST_SCREEN -> ListScreen()
            WEATHER_DETAILS_SCREEN -> WeatherDetailsScreen(data as Long)
            else -> throw IllegalArgumentException("unknown screen name: $screenName")
        }
}

internal class ListScreen : SupportAppScreen() {

    override fun getFragment(): Fragment = ListFragment.getInstance()
}

internal class WeatherDetailsScreen(private val cityId: Long) : SupportAppScreen() {

    override fun getFragment(): Fragment = DetailsFragment.getInstance(cityId = cityId)
}