package com.zkatemor.kudago.networks

interface ResponseCallback<R : ApiResponse> {
    fun onSuccess(apiResponse: R)
    fun onFailure(errorMessage: String)
}