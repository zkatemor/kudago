package com.zkatemor.kudago.util

import com.zkatemor.kudago.networks.EventsResponse
import com.zkatemor.kudago.networks.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsRepository {

    private object Holder {
        val INSTANCE = EventsRepository()
    }

    companion object {
        val instance: EventsRepository by lazy { Holder.INSTANCE }
    }

    fun getEvents(
        responseCallback: ResponseCallback<EventsResponse>
    ) {
        //retrofit async
        NetworkService.instance.service.getEvents()
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