package com.elyeproj.rxstate.presenter


import com.elyeproj.rxstate.model.DataModel
import com.elyeproj.rxstate.view.MainView
import io.reactivex.Observable
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class MainPresenterTest {

    @Mock
    lateinit var dataSource: DataSource

    @Mock
    lateinit var view: MainView

    lateinit var presenter: MainPresenter

    companion object {
        @ClassRule @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, dataSource)
    }

    @Test
    fun wheneverFetchSuccessWithDataShouldResultSuccessWithData() {
        // Given
        val data = DataModel("Data Loaded")
        val observable = Observable.just(data)

        // When
        whenever(dataSource.fetchData()).thenReturn(observable)
        presenter.loadData()

        // Then
        verify(view, times(1)).isLoading()
        verify(view, times(1)).isSuccess(any())
        verify(view, never()).isEmpty()
        verify(view, never()).isError(any())
    }

    @Test
    fun wheneverFetchSuccessWithoutDataShouldResultSuccessWithoutData() {
        // Given
        val data = DataModel("")
        val observable = Observable.just(data)

        // When
        whenever(dataSource.fetchData()).thenReturn(observable)
        presenter.loadData()

        // Then
        verify(view, times(1)).isLoading()
        verify(view, never()).isSuccess(any())
        verify(view, times(1)).isEmpty()
        verify(view, never()).isError(any())
    }

    @Test
    fun wheneverFetchErrorShouldResultError() {
        // Given
        val observable : Observable<DataModel> = Observable.create({
            subscriber -> subscriber.onError(IllegalArgumentException("Invalid Response"))
        })

        // When
        whenever(dataSource.fetchData()).thenReturn(observable)
        presenter.loadData()

        // Then
        verify(view, times(1)).isLoading()
        verify(view, never()).isSuccess(any())
        verify(view, never()).isEmpty()
        verify(view, times(1)).isError(any())
    }
}