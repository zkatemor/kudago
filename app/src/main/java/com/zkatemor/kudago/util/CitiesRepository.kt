package com.zkatemor.kudago.util

import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitiesRepository {

    private object Holder {
        val INSTANCE = CitiesRepository()
    }

    companion object {
        val instance: CitiesRepository by lazy { Holder.INSTANCE }
    }

    fun getCities(
        responseCallback: ResponseCallback<CitiesResponse>
    ) {
        //retrofit async
        NetworkService.instance.serviceCity.getCities()
            .enqueue(object : Callback<CitiesResponse> {

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {
                    responseCallback.onFailure("Getting cities error")
                }

                override fun onResponse(call: Call<CitiesResponse>, response: Response<CitiesResponse>) {
                    val citiesResponse = response.body()

                    if (citiesResponse != null) {
                        responseCallback.onSuccess(citiesResponse)
                    } else {
                        responseCallback.onFailure("Getting cities error")
                    }
                }
            })
    }
}