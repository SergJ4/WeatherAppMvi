package com.example.weather

import androidx.lifecycle.LifecycleOwner
import com.badoo.mvicore.android.AndroidBindings

class ListFragmentBindings(lifecycleOwner: LifecycleOwner) :
    AndroidBindings<ListFragment>(lifecycleOwner) {
    override fun setup(view: ListFragment) {
        binder.bind(view to view.viewModel.input)
        binder.bind(view.viewModel.output to view)
    }
}