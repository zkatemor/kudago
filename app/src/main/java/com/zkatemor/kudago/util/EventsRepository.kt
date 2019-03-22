package com.zkatemor.kudago.util

import com.zkatemor.kudago.networks.EventsResponse
import com.zkatemor.kudago.networks.ResponseCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class EventsRepository {

    private object Holder {
        val INSTANCE = EventsRepository()
    }

    companion object {
        val instance: EventsRepository by lazy { Holder.INSTANCE }
    }

   /* fun getEvents(): MutableLiveData<ScreenState<EventsState>> {

        val mutableLiveData = MutableLiveData<ScreenState<EventsState>>()
        mutableLiveData.value = ScreenState.Loading

        runOnNetworkIO {

            try {
                val eventsResponse = NetworkService.instance.service.getEvents().execute()

                runOnUi {

                    if (eventsResponse.isSuccessful) {
                        val events = eventsResponse.body()

                        if (events != null) {
                            mutableLiveData.value = ScreenState.Render(EventsState.EventsLoaded(events.transform().events))
                        } else {
                            mutableLiveData.value = ScreenState.Error(ApiException(0, "Getting events error"))
                        }
                    } else {
                        mutableLiveData.value = ScreenState.Error(ApiException(eventsResponse.code(), "Getting events error"))
                    }
                }
            } catch (ioException: IOException) {
                runOnUi { mutableLiveData.value = ScreenState.Error(ApiException(0, "Getting events error")) }
            }
        }

        return mutableLiveData
    }*/

    fun getEvents(responseCallback: ResponseCallback<EventsResponse>,
                  location: String,
                  page: Int) {
        //retrofit async
        NetworkService.instance.serviceEvent.getEvents(location, page)
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