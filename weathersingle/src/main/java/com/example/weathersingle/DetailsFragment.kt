package com.example.weathersingle

import android.os.Bundle
import android.view.View
import com.example.core.BaseFragment
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.city_weather_detail_layout.*
import javax.inject.Inject

internal const val CITY_ID_ARG = "city id"

class DetailsFragment : BaseFragment(),
    ObservableSource<DetailsFragment.UiEvent>,
    Consumer<DetailsFragment.Model> {

    @Inject
    internal lateinit var viewModel: DetailsFragmentViewModel

    private val adapter: FlexibleAdapter<IFlexible<*>> =
        FlexibleAdapter(null, null, true)

    override val layoutRes: Int = R.layout.city_weather_detail_layout

    sealed class UiEvent {
        object Refresh : UiEvent()
        object BackClicked : UiEvent()
    }

    data class Model(val listItems: List<IFlexible<*>>, val isLoading: Boolean)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (weatherList.adapter == null) {
            weatherList.adapter = adapter
        }
        DetailsFragmentBindings(viewLifecycleOwner).setup(this)
    }

    override fun subscribe(observer: Observer<in UiEvent>) {
        swipeRefresh.setOnRefreshListener { observer.onNext(UiEvent.Refresh) }
        backButton.setOnClickListener { observer.onNext(UiEvent.BackClicked) }
    }

    override fun accept(model: Model) {
        swipeRefresh.isRefreshing = model.isLoading
        adapter.updateDataSet(model.listItems)
    }

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