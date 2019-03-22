package com.zkatemor.kudago.util

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zkatemor.kudago.activities.MainActivity
import com.zkatemor.kudago.models.EventCard

class EventsViewModel : ViewModel() {

    var events : MutableLiveData<ArrayList<EventCard>> = MutableLiveData()

    init {
        events.value = MainActivity.eventCards
    }

    fun getEventsList() = events
}