package com.likemeal.domain

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class ObservableField<T> {

    private val valueSubject: PublishSubject<T> = PublishSubject.create()

    operator fun invoke(newValue: T) = valueSubject.onNext(newValue)

    operator fun invoke(): Observable<T> = valueSubject.hide()
}