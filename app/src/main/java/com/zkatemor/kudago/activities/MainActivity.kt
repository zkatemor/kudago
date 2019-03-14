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
import java.text.DateFormatSymbols
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
                setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
            }
        })
    }

    private fun setDataOnRecView(){
        val adapter = EventAdapter(eventCards)
        rec_view_event_card.layoutManager = LinearLayoutManager(this)
        rec_view_event_card.adapter = adapter

        adapter.onItemClick = { event ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("id", event.getId)
            intent.putExtra("title", event.getTitle)
            intent.putExtra("description", event.getDescription)
            intent.putExtra("fullDescription", event.getFullDesctiption)
            intent.putExtra("place", event.getLocation)
            intent.putExtra("date", event.getDate)
            intent.putExtra("price", event.getCost)
            intent.putExtra("images", event.getImageURL)
            startActivity(intent)
        }
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

        if (sDate != null) {
            result += sDay.toInt().toString()

            if (!sMonth.equals(eMonth))
                result += " " + DateFormatSymbols().getMonths()[sMonth.toInt() - 1]
        }

        if (eDate != null && !sDate.equals(eDate))
            result += " - " + eDay.toInt().toString() + " " + DateFormatSymbols().getMonths()[eMonth.toInt() - 1]

        return result
    }

    fun onClickCityButton(v: View) {
        val intent = Intent(this, CitiesActivity::class.java)
        startActivity(intent)
    }
}
