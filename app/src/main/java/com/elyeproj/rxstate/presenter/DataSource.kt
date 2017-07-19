package com.elyeproj.rxstate.presenter

import com.elyeproj.rxstate.model.DataModel
import io.reactivex.Observable

class DataSource {

    companion object {
        val FETCH_ERROR = 0
        val FETCH_SUCCESS = 1
        val FETCH_EMPTY = 2
    }
    var fetchStyle : Int = FETCH_ERROR

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
            FETCH_SUCCESS -> DataModel("Data Loaded")
            FETCH_EMPTY -> DataModel("")
            else -> {
                throw IllegalStateException("Error Fetching")
            }
        }
    }
}