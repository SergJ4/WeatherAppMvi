package com.example.serg.mvicoretest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.serg.mvicoretest.di.AppComponent
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

        //Yes I know it's ugly!! But Dagger is ugly itself!! Why Are you using it people???
        ((application as App).getAppComponent() as AppComponent)
            .mainActivity()
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
