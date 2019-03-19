package com.zkatemor.kudago.networks

import retrofit2.Call
import retrofit2.http.GET

interface CitiesService {
    @GET("locations/?lang=ru")
    fun getCities(): Call<CitiesResponse>
}