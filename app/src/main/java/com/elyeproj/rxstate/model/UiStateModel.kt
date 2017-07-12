package com.elyeproj.rxstate.model

data class UiStateModel private constructor(
        private val inProgress: Boolean = false,
        private val errorMessage: String? = null,
        private val dataModel: DataModel? = null
) {
    companion object {
        fun loading() = UiStateModel(inProgress = true)
        fun success(dataModel: DataModel) = UiStateModel(dataModel = dataModel)
        fun error(error: Throwable) = UiStateModel(errorMessage = error.message)
    }

    fun isLoading() = inProgress
    fun isError() = errorMessage != null
    fun isSuccess() = dataModel?.dataString?.isNotEmpty() ?: false && !isError()
    fun isEmpty() = !inProgress && errorMessage == null && !isSuccess()

    fun getErrorMessage() : String {
        if (errorMessage != null) {
            return errorMessage
        }
        throw IllegalStateException("errorMessage shouldn't be null")
    }

    fun getData() : DataModel {
        if (dataModel != null) {
            return dataModel
        }
        throw IllegalStateException("dataModel shouldn't be null")
    }
}
