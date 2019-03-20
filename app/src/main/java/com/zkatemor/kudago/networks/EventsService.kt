package com.zkatemor.kudago.networks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {
    @GET("events/?fields=id,dates,title,place,price,description,images,body_text&expand=place,dates"
                + "&order_by=-publication_date")

    fun getEvents(@Query("location") location: String,
                  @Query("page") page: Int)
            : Call<EventsResponse>
}