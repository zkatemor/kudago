package com.zkatemor.kudago.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.EventAdapter
import com.zkatemor.kudago.models.EventCard
import com.zkatemor.kudago.networks.*
import com.zkatemor.kudago.util.EventsRepository
import com.zkatemor.kudago.util.Tools
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private val tools: Tools by lazy(LazyThreadSafetyMode.NONE) { Tools(this) }
    private val DIRECTION_UP : Int = -1
    private val REQUEST_CODE_MESSAGE = 1
    private var eventCards: ArrayList<EventCard> = ArrayList()
    private var location: String = "msk"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (tools.isConnected()) {
            initializeSwipeRefreshLayoutListener()
            addEvents()
        }
        else {
            error_layout.visibility = View.VISIBLE
        }
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
                            tools.convertPlace(it.place),
                            tools.convertDate(it.dates[0].start_date, it.dates[0].end_date),
                            it.price,
                            currentImages,
                            tools.getCoordinates(it.place)
                        )
                    )
                }
                setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
                error_layout.visibility = View.VISIBLE
            }
        }
        ,location)
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
            intent.putExtra("fullDescription", event.getFullDescription)
            intent.putExtra("place", event.getLocation)
            intent.putExtra("date", event.getDate)
            intent.putExtra("price", event.getCost)
            intent.putExtra("images", event.getImages)
            intent.putExtra("coordinates", event.getCoordinates)
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
                swipe_refresh_layout.isEnabled = !(recyclerView?.canScrollVertically(DIRECTION_UP))
            }
        })
    }

    private fun initializeSwipeRefreshLayoutListener(){
        swipe_refresh_layout.setColorSchemeResources(R.color.colorRed)
        progress_bar_layout.visibility = View.VISIBLE

        swipe_refresh_layout.setOnRefreshListener {
            if (tools.isConnected()) {
                eventCards = ArrayList()
                addEvents()
            }
            else
                error_layout.visibility = View.VISIBLE
        }
    }

    fun onClickCityButton(v: View) {
        val intent = Intent(this, CitiesActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_MESSAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_MESSAGE -> {
                    location = data!!.getStringExtra("location")
                    text_view_city.text = data!!.getStringExtra("cityName")
                    eventCards = ArrayList()
                    addEvents()
                }
            }
        }
    }
}