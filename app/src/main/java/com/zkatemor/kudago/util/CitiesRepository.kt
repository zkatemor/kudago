package com.zkatemor.kudago.util

import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.CitiesService
import com.zkatemor.kudago.networks.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CitiesRepository @Inject constructor(private val citiesApi: CitiesService){

    fun getCities(responseCallback: ResponseCallback<ArrayList<CitiesResponse>>) {
        //retrofit async
        citiesApi.getCities()
            .enqueue(object : Callback<ArrayList<CitiesResponse>> {

            override fun onFailure(call: Call<ArrayList<CitiesResponse>>, t: Throwable) {
                responseCallback.onFailure("Getting cities error")
            }

            override fun onResponse(call: Call<ArrayList<CitiesResponse>>, response: Response<ArrayList<CitiesResponse>>
            ) {
                val citiesResponse = response.body()

                if (citiesResponse != null && response.isSuccessful) {
                    responseCallback.onSuccess(citiesResponse)
                } else {
                    responseCallback.onFailure("Getting cities error")
                }
            }
        })
    }
}