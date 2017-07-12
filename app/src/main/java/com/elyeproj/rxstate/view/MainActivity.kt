package com.elyeproj.rxstate.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.elyeproj.rxstate.presenter.MainPresenter
import com.elyeproj.rxstate.R
import com.elyeproj.rxstate.model.DataModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val mainPresenter by lazy {
        MainPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_load_success.setOnClickListener {
            mainPresenter.loadSuccess()
        }

        btn_load_error.setOnClickListener {
            mainPresenter.loadError()
        }

        btn_load_empty.setOnClickListener {
            mainPresenter.loadEmpty()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.destroy()
    }

    override fun isEmpty() {
        status_view.isEmpty()
    }

    override fun isLoading() {
        status_view.isLoading()
    }

    override fun isSuccess(data: DataModel) {
        status_view.isSuccess(data.dataString)
    }

    override fun isError(errorMessage: String) {
        status_view.isError(errorMessage)
    }
}
