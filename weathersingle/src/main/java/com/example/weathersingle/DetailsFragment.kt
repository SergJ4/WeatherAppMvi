package com.example.weathersingle

import android.os.Bundle
import androidx.fragment.app.Fragment

internal const val CITY_ID_ARG = "city id"

class DetailsFragment : Fragment() {

    companion object {
        fun getInstance(cityId: Long): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putLong(CITY_ID_ARG, cityId)
            fragment.arguments = args
            return fragment
        }
    }
}