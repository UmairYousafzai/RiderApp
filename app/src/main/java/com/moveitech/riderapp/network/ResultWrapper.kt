package com.moveitech.riderapp.network

import com.moveitech.riderapp.dataModel.utilModel.ApiErrorResponse

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: ApiErrorResponse? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
    object ConnectNetworkError: ResultWrapper<Nothing>()
}