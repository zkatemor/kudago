package com.zkatemor.kudago.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.EventAdapter
import com.zkatemor.kudago.models.EventCard
import com.zkatemor.kudago.networks.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val eventCards: ArrayList<EventCard> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addEvents()
        rec_view_event_card.layoutManager = LinearLayoutManager(this)
        rec_view_event_card.adapter = EventAdapter(eventCards)
    }

    private fun addEvents() {
        /* eventCards.add(EventCard("Title", "Description", "Location", "Date", 0,
             R.drawable.image_event_card))
         eventCards.add(EventCard("Title1", "Description1", "Location1", "Date1", 1,
             R.drawable.image_event_card))
         eventCards.add(EventCard("Title2", "Description2", "Location2", "Date2", 2,
             R.drawable.image_event_card))*/

        EventsRepository.instance.getEvents(object : ResponseCallback<EventsResponse> {

            override fun onSuccess(apiResponse: EventsResponse) {
                apiResponse.events.forEach {
                    eventCards.add(
                        EventCard(
                            it.id,
                            it.title,
                            it.description,
                            it.fullDescription,
                            convertPlace(it.place),
                            convertDate(
                                it.dates[0].start_date,
                                it.dates[0].end_date
                            )
                            ,
                            it.price,
                            it.images[0].image
                        )
                    )
                }
            }

            override fun onFailure(errorMessage: String) {
               // text.text = errorMessage
            }
        })
    }

    private fun convertPlace(place: Place?): String {
        var result: String = ""

        if (place != null) {
            if (place.title != null)
                result += place.title

            if (place.address != null)
                result += place.address
        }

        return result
    }

    private fun convertDate(sDate: String?, eDate: String?): String {
        var result: String = ""



        return result
    }

    fun onClickEventCard(v: View) {
        val intent = Intent(this, EventActivity::class.java)
        startActivity(intent)
    }

    fun onClickCityButton(v: View) {
        val intent = Intent(this, CitiesActivity::class.java)
        startActivity(intent)
    }
}
