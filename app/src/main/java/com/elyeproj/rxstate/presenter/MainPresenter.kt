package com.elyeproj.rxstate.presenter

import com.elyeproj.rxstate.model.UiStateModel
import com.elyeproj.rxstate.presenter.DataSource.FetchStyle.*
import com.elyeproj.rxstate.view.MainView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainPresenter(val view: MainView) {

    private val dataSource = DataSource()
    private var disposable: Disposable? = null

    fun loadSuccess() {
        dataSource.fetchStyle = FETCH_SUCCESS
        loadData()
    }


    fun loadError() {
        dataSource.fetchStyle = FETCH_ERROR
        loadData()
    }

    fun loadEmpty() {
        dataSource.fetchStyle = FETCH_EMPTY
        loadData()
    }

    private fun loadData() {
        disposable?.dispose()

        disposable = dataSource.fetchData()
                .map { result -> UiStateModel.success(result) }
                .onErrorReturn { exception -> UiStateModel.error(exception) }
                .startWith(UiStateModel.loading())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    uiState ->
                    when {
                        uiState.isLoading() -> view.isLoading()
                        uiState.isError() -> view.isError(uiState.getErrorMessage())
                        uiState.isSuccess() -> view.isSuccess(uiState.getData())
                        uiState.isEmpty() -> view.isEmpty()
                        else -> IllegalArgumentException("Invalid Response")
                    }
                })
    }

    fun destroy() {
        disposable?.dispose()
    }
}