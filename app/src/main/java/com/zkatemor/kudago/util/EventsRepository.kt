package com.zkatemor.kudago.util

import com.zkatemor.kudago.networks.EventsResponse
import com.zkatemor.kudago.networks.EventsService
import com.zkatemor.kudago.networks.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class EventsRepository @Inject constructor(private val eventsApi: EventsService) {
    fun getEvents(responseCallback: ResponseCallback<EventsResponse>,
                  location: String,
                  page: Int) {
        //retrofit async
        eventsApi.getEvents(location, page)
            .enqueue(object : Callback<EventsResponse> {

                override fun onFailure(call: Call<EventsResponse>, t: Throwable) {
                    responseCallback.onFailure("Getting events error")
                }

                override fun onResponse(call: Call<EventsResponse>, response: Response<EventsResponse>) {
                    val eventsResponse = response.body()

                    if (eventsResponse != null) {
                        responseCallback.onSuccess(eventsResponse)
                    } else {
                        responseCallback.onFailure("Getting events error")
                    }
                }
            })
    }
}