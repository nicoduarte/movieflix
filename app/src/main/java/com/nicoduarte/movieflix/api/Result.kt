package com.nicoduarte.movieflix.api

data class Result<out T>(val status: ResultStatus, val data: T?, val error: String?) {
    companion object {
        fun <T> success(data: T): Result<T> = Result(status = ResultStatus.SUCCESS, data = data, error = null)

        fun <T> error(data: T? = null, message: String? = null): Result<T> =
            Result(status = ResultStatus.ERROR, data = data, error = message)

        fun <T> loading(data: T? = null): Result<T> =
            Result(status = ResultStatus.LOADING, data = data, error = null)
    }

    fun setState(onSuccess: (T) -> Unit, onError: ((String) -> Unit)? = null, onLoading: ((T?) -> Unit)? = null) {
        when (status) {
            ResultStatus.SUCCESS -> {
                onSuccess(data!!)
            }
            ResultStatus.ERROR -> {
                onError?.let { it(error ?: "") }
            }
            ResultStatus.LOADING -> {
                onLoading?.let { it(data) }
            }
        }
    }
}