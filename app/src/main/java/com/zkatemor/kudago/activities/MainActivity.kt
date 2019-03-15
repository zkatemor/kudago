package com.zkatemor.kudago.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.EventAdapter
import com.zkatemor.kudago.models.EventCard
import com.zkatemor.kudago.networks.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormatSymbols
import java.util.*

class MainActivity : AppCompatActivity() {

    private val DIRECTION_UP : Int = -1
    private var eventCards: ArrayList<EventCard> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeSwipeRefreshLayoutListener()
        addEvents()
    }

    private fun addEvents() {
        EventsRepository.instance.getEvents(object : ResponseCallback<EventsResponse> {

            override fun onSuccess(apiResponse: EventsResponse) {
                apiResponse.events.forEach {
                    val currentImages = ArrayList<String>()
                    it.images.forEach {
                        currentImages.add(it.image)
                    }

                    eventCards.add(
                        EventCard(
                            it.id,
                            it.title,
                            it.description,
                            it.fullDescription,
                            convertPlace(it.place),
                            convertDate(it.dates[0].start_date, it.dates[0].end_date),
                            it.price,
                            currentImages
                        )
                    )
                }
                setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
                error_layout.visibility = View.VISIBLE
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
            intent.putExtra("images", event.getImages)
            startActivity(intent)
        }

        initializeScrollListenerOnRecView()

        swipe_refresh_layout.isRefreshing = false
        progress_bar_layout.visibility = View.INVISIBLE
    }

    private fun initializeScrollListenerOnRecView(){
        rec_view_event_card.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipe_refresh_layout.isEnabled = !(recyclerView?.canScrollVertically(DIRECTION_UP) ?: return)
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

        if (sDate != null) {
            result += sDay.toInt().toString()

            if (!sMonth.equals(eMonth))
                result += " " + DateFormatSymbols().getMonths()[sMonth.toInt() - 1]
        }

        if (eDate != null && !sDate.equals(eDate))
            result += " - " + eDay.toInt().toString() + " " + DateFormatSymbols().getMonths()[eMonth.toInt() - 1]

        return result
    }

    private fun initializeSwipeRefreshLayoutListener(){
        swipe_refresh_layout.setColorSchemeResources(R.color.colorRed)
        swipe_refresh_layout.setOnRefreshListener {
            progress_bar_layout.visibility = View.VISIBLE
            eventCards = ArrayList()
            addEvents()
        }
    }

    fun onClickCityButton(v: View) {
        val intent = Intent(this, CitiesActivity::class.java)
        startActivity(intent)
    }
}
