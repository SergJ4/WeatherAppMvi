package com.example.weathersingle

import com.badoo.mvicore.element.Actor
import com.badoo.mvicore.element.Reducer
import com.badoo.mvicore.feature.ActorReducerFeature
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Router
import io.reactivex.Observable
import javax.inject.Inject

@FragmentScope
class DetailsFragmentFeature @Inject constructor(router: Router) :
    ActorReducerFeature<
            DetailsFragmentFeature.Wish,
            DetailsFragmentFeature.Effect,
            DetailsFragmentFeature.State,
            DetailsFragmentFeature.News>(
        initialState = State(false),
        reducer = ReducerImpl(),
        actor = ActorImpl(router)
    ) {

    sealed class Wish {
        object Refresh : Wish()
        object Back : Wish()
    }

    sealed class Effect {

    }

    data class State(val isLoading: Boolean)

    sealed class News {

    }

    class ReducerImpl : Reducer<State, Effect> {
        override fun invoke(state: State, effect: Effect): State {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    class ActorImpl(router: Router) : Actor<State, Wish, Effect> {
        override fun invoke(state: State, wish: Wish): Observable<out Effect> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}