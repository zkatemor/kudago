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
import java.util.*


class MainActivity : AppCompatActivity() {

    private val eventCards: ArrayList<EventCard> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addEvents()
    }

    private fun addEvents() {
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
                            convertDate(it.dates[0].start_date, it.dates[0].end_date),
                            it.price,
                            it.images[0].image
                        )
                    )
                }
                rec_view_event_card.layoutManager = LinearLayoutManager(this@MainActivity)
                rec_view_event_card.adapter = EventAdapter(eventCards)
            }

            override fun onFailure(errorMessage: String) {
            }
        })
    }

    private fun convertPlace(place: Place?): String {
        var result: String = ""

        if (place != null) {
            if (place.title != null)
                result += place.title
            else
                if (place.address != null)
                    result += place.address
        }

        return result
    }

    private fun convertDate(sDate: String?, eDate: String?): String {
        var result: String = ""
        var sMonth: String = ""
        var sDay: String = ""
        var eMonth: String = ""
        var eDay: String = ""

        if (sDate != null) {
            sMonth += sDate!!.substring(5, 7)
            sDay += sDate!!.substring(8)
        }

        if (eDate != null && !sDate.equals(eDate)) {
            eMonth += eDate!!.substring(5, 7)
            eDay += eDate!!.substring(8)
        }

        val monthNames = arrayOf(
            "января",
            "февраля",
            "марта",
            "апреля",
            "мая",
            "июня",
            "июля",
            "августа",
            "сентября",
            "октября",
            "ноября",
            "декабря"
        )

        if (sDate != null) {
            result += sDay.toInt().toString()

            if (!sMonth.equals(eMonth))
                result += " " + monthNames[sMonth.toInt() - 1]
        }

        if (eDate != null && !sDate.equals(eDate))
            result += " - " + eDay.toInt().toString() + " " + monthNames[eMonth.toInt() - 1]

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
