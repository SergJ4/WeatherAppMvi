package com.example.serg.mvicoretest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class AppNavigator(activity: MainActivity, fragmentManager: FragmentManager, containerId: Int) :
    SupportAppNavigator(activity, fragmentManager, containerId) {

    override fun setupFragmentTransaction(
        command: Command?,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)

        if (currentFragment == null) {
            fragmentTransaction?.setCustomAnimations(
                0,
                0,
                R.anim.slide_in_left,
                R.anim.slide_out_left
            )
        } else {
            fragmentTransaction?.setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }
    }
}