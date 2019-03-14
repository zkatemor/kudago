package com.zkatemor.kudago.networks

import retrofit2.Call
import retrofit2.http.GET

const val BASE_URL = "https://kudago.com/public-api/v1.4/"

interface EventsService {
    @GET(
        "events/?fields=id,dates,title,place,price,description,images,body_text&expand=place,dates"
                + "&order_by=-publication_date&text_format=text&location=msk"
    )
    fun getEvents(): Call<EventsResponse>
}