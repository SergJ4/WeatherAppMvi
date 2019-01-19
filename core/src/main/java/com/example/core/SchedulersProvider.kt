package com.example.core

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Provides Schedulers for Rx chains
 */
class SchedulersProvider {

    companion object {
        fun io() = Schedulers.io()

        fun computation() = Schedulers.computation()

        fun ui(): Scheduler = AndroidSchedulers.mainThread()
    }
}