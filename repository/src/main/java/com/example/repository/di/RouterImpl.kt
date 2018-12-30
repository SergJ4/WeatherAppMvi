package com.example.repository.di

import androidx.fragment.app.Fragment
import com.example.core.interfaces.navigation.NavigationModule
import com.example.core.interfaces.navigation.Router
import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * I tried to move all dependencies on third-party frameworks to repository.
 * That's why it's here
 */
internal class RouterImpl(private val router: ru.terrakok.cicerone.Router) : Router {

    private val modules = mutableSetOf<NavigationModule>()

    override fun installModule(module: NavigationModule) {
        modules.add(module)
    }

    override fun uninstallModule(module: NavigationModule) {
        modules.remove(module)
    }

    override fun goTo(screen: com.example.core.interfaces.navigation.Screen) {
        val module = modules.firstOrNull { it.isScreenNameSupported(screen.screenName) }
        if (module == null) {
            throw IllegalStateException("No module provided to navigate to $screen.screenName")
        } else {
            router.navigateTo(createScreen(module, screen))
        }
    }

    override fun rootScreen(screen: com.example.core.interfaces.navigation.Screen) {
        val module = modules.firstOrNull { it.isScreenNameSupported(screen.screenName) }
        if (module == null) {
            throw IllegalStateException("No module provided to navigate to $screen.screenName")
        } else {
            router.newRootScreen(createScreen(module, screen))
        }
    }

    override fun back() = router.exit()
}

private fun createScreen(
    module: NavigationModule,
    screen: com.example.core.interfaces.navigation.Screen
): Screen = object : SupportAppScreen() {
    override fun getFragment(): Fragment {
        return module.getFragment(screenName = screen.screenName, data = screen.data)
    }
}