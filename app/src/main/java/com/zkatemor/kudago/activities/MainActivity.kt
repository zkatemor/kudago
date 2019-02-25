package com.zkatemor.kudago.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.EventAdapter
import com.zkatemor.kudago.models.EventCard
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val event_cards: ArrayList<EventCard> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addEvents()
        rv_event_card.layoutManager = LinearLayoutManager(this)
        rv_event_card.adapter = EventAdapter(event_cards)
    }

    private fun addEvents(){
        event_cards.add(EventCard("Title", "Description", "Location", "Date", 0,
            R.drawable.ic_image_event_card))
        event_cards.add(EventCard("Title1", "Description1", "Location1", "Date1", 1,
            R.drawable.ic_image_event_card))
        event_cards.add(EventCard("Title2", "Description2", "Location2", "Date2", 2,
            R.drawable.ic_image_event_card))
    }
}
