package com.zkatemor.kudago.activities

import android.app.Activity
import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.zkatemor.kudago.adapters.EventAdapter
import com.zkatemor.kudago.models.EventCard
import com.zkatemor.kudago.networks.*
import com.zkatemor.kudago.util.EventsRepository
import com.zkatemor.kudago.util.Tools
import java.io.Serializable
import kotlin.collections.ArrayList
import com.zkatemor.kudago.R
import com.zkatemor.kudago.util.ShowError
import android.content.Intent
import com.zkatemor.kudago.app.App
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity() {

    @Inject
    @Named("Events_Repository")
    lateinit var repository: EventsRepository

    var citySettings: SharedPreferences? = null
    val APP_PREFERENCES = "city_settings"
    val APP_PREFERENCES_CITY = "city"
    val APP_PREFERENCES_CITY_NAME = "cityName"

    val PAGE_KEY = "page"
    val EVENTSCARD_KEY = "eventsCard"
    val CITYNAME_KEY = "cityName"
    val LOCATION_KEY = "location"

    private val BROADCAST_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    private val INTENT_FILTER = IntentFilter(BROADCAST_ACTION)
    private val DIRECTION_UP: Int = -1
    private val REQUEST_CODE_MESSAGE = 1
    private var eventCards: ArrayList<EventCard> = ArrayList()
    private var location: String = "msk"
    private var page: Int = 1
    private var isLoadData: Boolean = true
    private var isInternetAccess: Boolean = false

    private val broadcast_receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BROADCAST_ACTION -> {
                    isInternetAccess = false

                    val checkInternet = CoroutineScope(Dispatchers.IO).async {
                        Tools.isInternetAccess(this@MainActivity)
                    }

                    GlobalScope.launch(Dispatchers.Main) {
                        isInternetAccess = checkInternet.await()
                        if (isInternetAccess) {
                            showEvents()
                        } else {
                            showLackInternet()
                        }
                    }
                }
            }
        }
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.clear()
        savedInstanceState.putSerializable(EVENTSCARD_KEY, eventCards as Serializable)
        savedInstanceState.putInt(PAGE_KEY, page)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        App.component!!.injectsMainActivity(this)

        citySettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        readCurrentCity()

        initializeSwipeRefreshLayoutListener()

        if (savedInstanceState != null) {
            eventCards = savedInstanceState.getSerializable(EVENTSCARD_KEY) as ArrayList<EventCard>
            page = savedInstanceState.getInt(PAGE_KEY)
            loadEvents()
        } else {
            showEvents()
        }
    }

    private fun showLackInternet() {
        invisibleProgress()
        error_layout.visibility = View.VISIBLE
        val error_snackbar = ShowError(error_layout)
        error_snackbar.show(this)
    }

    private fun showEvents() {
        if (eventCards.size == 0) {
            eventCards = ArrayList()
            page = 1
            initializeData()
        } else {
            visibleEventsLayout()
        }
    }

    private fun initializeData() {
        invisibleErrorLayout()
        visibleProgress()
        isLoadData = true
        addEvents()
    }


    private fun readCurrentCity() {
        if (citySettings!!.contains(APP_PREFERENCES_CITY)) {
            location = citySettings!!.getString(APP_PREFERENCES_CITY, "msk") as String
        }

        if (citySettings!!.contains(APP_PREFERENCES_CITY_NAME)) {
            text_view_city.text = citySettings!!.getString(APP_PREFERENCES_CITY_NAME, "Москва") as String
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcast_receiver, INTENT_FILTER)
        readCurrentCity()
    }

    private fun saveCurrentCity() {
        val editor = citySettings!!.edit()
        editor.putString(APP_PREFERENCES_CITY, location)
        editor.putString(APP_PREFERENCES_CITY_NAME, text_view_city.text.toString())
        editor.apply()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcast_receiver)
        saveCurrentCity()
    }

    override fun onStop() {
        super.onStop()
        saveCurrentCity()
    }

    private fun loadEvents() {
        isLoadData = false
        setDataOnRecView()
    }

    private fun addEvents() {
        repository.getEvents(object : ResponseCallback<EventsResponse> {
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
                            Tools.convertPlace(it.place),
                            Tools.convertDate(it.dates[0].start_date, it.dates[0].end_date),
                            it.price,
                            currentImages,
                            Tools.getCoordinates(it.place)
                        )
                    )
                }

                isLoadData = false

                if (page > 1) {
                    rec_view_event_card.adapter!!.notifyItemInserted(eventCards.size - 1)
                } else
                    setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
                showLackInternet()
                isLoadData = false
            }
        }
            , location
            , page)
    }

    private fun invisibleProgress() {
        swipe_refresh_layout.isRefreshing = false
        progress_bar_layout.visibility = View.INVISIBLE
    }

    private fun visibleProgress() {
        progress_bar_layout.visibility = View.VISIBLE
    }

    private fun visibleEventsLayout() {
        main_frame_layout.visibility = View.VISIBLE
        error_layout.visibility = View.INVISIBLE
    }

    private fun invisibleErrorLayout() {
        error_layout.visibility = View.INVISIBLE
    }

    private fun setDataOnRecView() {
        val adapter = EventAdapter(eventCards, this)
        val manager = LinearLayoutManager(this)
        rec_view_event_card.layoutManager = manager
        rec_view_event_card.adapter = adapter

        adapter.onItemClick = { event ->
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("event", event)
            startActivity(intent)
        }

        initializeScrollListenerOnRecView(manager)

        invisibleProgress()
    }

    private fun initializeScrollListenerOnRecView(manager: LinearLayoutManager) {
        rec_view_event_card.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                swipe_refresh_layout.isEnabled = !(recyclerView?.canScrollVertically(DIRECTION_UP))

                val countVisible = manager.childCount
                val countGeneral = manager.itemCount
                val firstPosition = manager.findFirstVisibleItemPosition()

                if (!isLoadData && isInternetAccess) {
                    if ((countVisible + firstPosition) >= countGeneral) {
                        page++
                        isLoadData = true
                        addEvents()
                    }
                }
            }
        })
    }

    private fun initializeSwipeRefreshLayoutListener() {
        swipe_refresh_layout.setColorSchemeResources(R.color.colorRed)
        visibleProgress()

        swipe_refresh_layout.setOnRefreshListener {
            if (isInternetAccess) {
                eventCards = ArrayList()
                page = 1
                invisibleErrorLayout()
                visibleEventsLayout()
                initializeData()
            } else
                showLackInternet()
        }
    }

    fun onClickCityButton(v: View) {
        val intent = Intent(this, CitiesActivity::class.java)
        intent.putExtra(LOCATION_KEY, location)
        startActivityForResult(intent, REQUEST_CODE_MESSAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_MESSAGE -> {
                    location = data!!.getStringExtra(LOCATION_KEY)
                    text_view_city.text = data!!.getStringExtra(CITYNAME_KEY)
                    saveCurrentCity()
                    eventCards = ArrayList()
                    progress_bar_layout.visibility = View.VISIBLE
                    page = 1
                    initializeData()
                }
            }
        }
    }
}