package com.example.weathersingle

import androidx.lifecycle.LifecycleOwner
import com.badoo.mvicore.android.AndroidBindings

class DetailsFragmentBindings(lifecycleOwner: LifecycleOwner) :
    AndroidBindings<DetailsFragment>(lifecycleOwner) {
    override fun setup(view: DetailsFragment) {
        binder.bind(view to view.viewModel.input)
        binder.bind(view.viewModel.output to view)
    }
}