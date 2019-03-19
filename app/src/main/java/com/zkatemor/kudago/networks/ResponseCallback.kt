package com.zkatemor.kudago.networks

const val BASE_URL = "https://kudago.com/public-api/v1.4/"

interface ResponseCallback<R : ApiResponse> {
    fun onSuccess(apiResponse: R)
    fun onFailure(errorMessage: String)
}