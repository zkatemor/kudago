package com.zkatemor.kudago.activities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.CityAdapter
import com.zkatemor.kudago.models.City
import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.ResponseCallback
import com.zkatemor.kudago.util.Base
import com.zkatemor.kudago.util.CitiesRepository
import com.zkatemor.kudago.util.CitiesViewModel
import com.zkatemor.kudago.util.Tools
import kotlinx.android.synthetic.main.activity_cities.*
import kotlinx.android.synthetic.main.activity_main.*

class CitiesActivity : AppCompatActivity() {

    private val tools: Tools by lazy(LazyThreadSafetyMode.NONE) { Tools(this) }
    private var cities: ArrayList<City> = ArrayList()
    private lateinit var adapter: CityAdapter
    private lateinit var viewModel: CitiesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        if (tools.isConnected()) {
            addCities()
            setDataOnRecView()
        } else {
            error_layout.visibility = View.VISIBLE
        }
    }

    private fun addCities() {
        if (cities.size == 0) {
            cities = ArrayList()
            initViewModel()
        } else {
            progress_bar_city.visibility = View.INVISIBLE
        }
    }

    private fun setDataOnRecView() {
        val data = intent.extras
        adapter = CityAdapter(cities, data?.getString("location"))
        rec_view_cities.layoutManager = LinearLayoutManager(this)

        adapter.onItemClick = { city ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("location", city.getSlug)
            intent.putExtra("cityName", city.getCityName)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        rec_view_cities.adapter = adapter
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProviders.of(this, Base { CitiesViewModel() }).get(CitiesViewModel::class.java)

        viewModel.getCities().observe(this, object : Observer<ArrayList<City>> {
            override fun onChanged(citiesList: ArrayList<City>?) {
                if (citiesList != null && cities != citiesList) {
                    progress_bar_layout_city.visibility = View.VISIBLE
                    cities = citiesList
                    adapter.setItems(cities)
                }
                progress_bar_layout_city.visibility = View.INVISIBLE
            }
        })
    }

    fun onClickCross(v: View) {
        onBackPressed()
    }
}