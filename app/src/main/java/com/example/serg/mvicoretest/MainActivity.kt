package com.example.serg.mvicoretest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.serg.mvicoretest.di.AppInjector
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val navigator = AppNavigator(
        this,
        supportFragmentManager,
        R.id.fragmentsContainer
    )

    @Inject
    internal lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppInjector
            .appComponent
            .mainActivityComponent()
            .build()
            .inject(this)

        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()

        navigatorHolder.removeNavigator()
    }
}
