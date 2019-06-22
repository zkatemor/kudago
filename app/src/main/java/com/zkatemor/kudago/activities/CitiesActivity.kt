package com.zkatemor.kudago.activities

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.CityAdapter
import com.zkatemor.kudago.app.App
import com.zkatemor.kudago.models.City
import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.ResponseCallback
import com.zkatemor.kudago.util.CitiesRepository
import com.zkatemor.kudago.util.ShowError
import com.zkatemor.kudago.util.Tools
import kotlinx.android.synthetic.main.activity_cities.*
import kotlinx.android.synthetic.main.activity_cities.error_layout_city
import kotlinx.android.synthetic.main.activity_cities.progress_bar_layout
import kotlinx.coroutines.*
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Named

class CitiesActivity : AppCompatActivity() {
    @Inject
    @Named("Cities_Repository")
    lateinit var repository: CitiesRepository

    private val BROADCAST_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
    private val INTENT_FILTER = IntentFilter(BROADCAST_ACTION)
    private var cities: ArrayList<City> = ArrayList()
    private var slug: String = ""
    private var isLoadData: Boolean = true
    private var isInternetAccess: Boolean = false

    private val broadcast_receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BROADCAST_ACTION -> {
                    isInternetAccess = false

                    val checkInternet = CoroutineScope(Dispatchers.IO).async {
                        Tools.isInternetAccess(this@CitiesActivity)
                    }

                    GlobalScope.launch(Dispatchers.Main) {
                        isInternetAccess = checkInternet.await()
                        if (isInternetAccess) {
                            showCities()
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
        savedInstanceState.putSerializable("cities", cities as Serializable)
        savedInstanceState.putString("slug", slug)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        App.component!!.injectsCitiesActivity(this)

        setSlug()

        if (savedInstanceState != null) {
            cities = savedInstanceState.getSerializable("cities") as ArrayList<City>
            slug = savedInstanceState.getString("slug") as String
            setDataOnRecView()
        } else {
            if (Tools.isInternetAccess(this)) {
                addCities()
            } else {
                showLackInternet()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcast_receiver, INTENT_FILTER)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcast_receiver)
    }

    fun showCities(){
        if (cities.size == 0) {
            cities = ArrayList()
            addCities()
        } else {
            city_main_layout.visibility = View.VISIBLE
            error_layout_city.visibility = View.INVISIBLE
        }
    }

    private fun showLackInternet() {
        error_layout_city.visibility = View.VISIBLE
        val error_snackbar = ShowError(error_layout_city)
        error_snackbar.show(this)
    }

    private fun setSlug(){
        val data = intent.extras
        slug = data?.getString("location") as String
    }

    private fun addCities() {
        cities = ArrayList()
        isLoadData = true
        repository.getCities(object : ResponseCallback<ArrayList<CitiesResponse>> {

            override fun onSuccess(apiResponse: ArrayList<CitiesResponse>) {
                var tmpCities: ArrayList<City> = ArrayList()

                apiResponse.forEach {
                    tmpCities.add(City(it.name, it.slug))
                }

                cities.addAll(Tools.sortCities(tmpCities))
                setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
                showLackInternet()
            }
        })
    }

    private fun setDataOnRecView() {
        val adapter = CityAdapter(cities, slug)
        rec_view_cities.layoutManager = LinearLayoutManager(this)
        rec_view_cities.adapter = adapter

        adapter.onItemClick = { city ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("location", city.getSlug)
            intent.putExtra("cityName", city.getCityName)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        progress_bar_layout.visibility = View.INVISIBLE

    }

    fun onClickCross(v: View) {
        onBackPressed()
    }
}