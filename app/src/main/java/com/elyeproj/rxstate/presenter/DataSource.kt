package com.elyeproj.rxstate.presenter

import com.elyeproj.rxstate.model.DataModel
import io.reactivex.Observable

class DataSource {

    enum class FetchStyle {
        FETCH_SUCCESS,
        FETCH_EMPTY,
        FETCH_ERROR
    }

    var fetchStyle = FetchStyle.FETCH_ERROR

    fun fetchData(): Observable<DataModel> {
        return Observable.create({
            subscriber ->
            try {
                subscriber.onNext(loadData())
                subscriber.onComplete()
            } catch (exception: Exception) {
                if (!subscriber.isDisposed) {
                    subscriber.onError(exception)
                }
            }
        })
    }

    private fun loadData(): DataModel {
        Thread.sleep(2500)
        return when (fetchStyle) {
            FetchStyle.FETCH_SUCCESS -> DataModel("Data Loaded")
            FetchStyle.FETCH_EMPTY -> DataModel("")
            FetchStyle.FETCH_ERROR -> throw IllegalStateException("Error Fetching")
        }
    }
}