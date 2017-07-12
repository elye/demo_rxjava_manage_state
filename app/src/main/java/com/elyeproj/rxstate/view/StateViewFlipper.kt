package com.elyeproj.rxstate.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.ViewFlipper
import com.elyeproj.rxstate.R

class StateViewFlipper(context: Context, attrs: AttributeSet?
) : ViewFlipper(context, attrs) {

    val successStateView by lazy {
            LayoutInflater.from(context).inflate(
                    R.layout.view_success, this, false)
    }

    val loadingStateView by lazy {
            LayoutInflater.from(context).inflate(
                    R.layout.view_loading, this, false)
    }

    val errorStateView by lazy {
            LayoutInflater.from(context).inflate(
                    R.layout.view_error, this, false)
    }

    val emptyStateView by lazy {
        LayoutInflater.from(context).inflate(
                R.layout.view_empty, this, false)
    }

    init {
        // Animation Preferences
        setInAnimation(context, android.R.anim.fade_in)
        setOutAnimation(context, android.R.anim.fade_out)

        isEmpty()
    }

    private fun showView(view: View) {
        if (indexOfChild(view) == -1) {
            addView(view)
        }
        if (currentView != view) {
            // Flip to specific view
            displayedChild = indexOfChild(view)
        }
    }

    fun isSuccess(data: String) {
        successStateView.findViewById<TextView>(R.id.txt_data).text = data
        showView(successStateView)
    }

    fun isError(message: String) {
        errorStateView.findViewById<TextView>(R.id.txt_error).text = message
        showView(errorStateView)
    }

    fun isEmpty() {
        showView(emptyStateView)
    }

    fun isLoading() {
        showView(loadingStateView)
    }
}