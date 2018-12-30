package com.example.serg.mvicoretest.di

import com.example.core.di.ScopedComponent
import com.example.serg.mvicoretest.App
import com.example.serg.mvicoretest.MainActivity

class MainActivityScopedComponent(
    private val activity: MainActivity,
    private val fragmentsContainerId: Int
) :
    ScopedComponent<MainActivityComponent>() {

    override fun create(): MainActivityComponent {
        return DaggerMainActivityComponent
            .builder()
            .appProvider(
                App
                    .component
                    .dependAndGet(this)!!
            )
            .activity(activity)
            .fragmentSupportManager(activity.supportFragmentManager)
            .fragmentsContainerId(fragmentsContainerId)
            .build()
    }
}