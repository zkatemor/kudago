package com.zkatemor.kudago.networks

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsService {
    @GET(
        "events/?fields=id,dates,title,place,price,description,images,body_text&expand=place,dates"
                + "&order_by=-publication_date&text_format=text"
    )
    fun getEvents(@Query("location") location: String): Call<EventsResponse>
}