package com.zkatemor.kudago.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.CityAdapter
import com.zkatemor.kudago.models.City
import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.ResponseCallback
import com.zkatemor.kudago.util.CitiesRepository
import com.zkatemor.kudago.util.Tools
import kotlinx.android.synthetic.main.activity_cities.*

class CitiesActivity : AppCompatActivity() {

    private val tools: Tools by lazy(LazyThreadSafetyMode.NONE) { Tools(this) }
    private var cities: ArrayList<City> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        addCities()
    }

    private fun addCities() {
        CitiesRepository.instance.getCities(object : ResponseCallback<ArrayList<CitiesResponse>> {

            override fun onSuccess(apiResponse: ArrayList<CitiesResponse>) {
                var tmpCities: ArrayList<City> = ArrayList()

                apiResponse.forEach {
                    tmpCities.add(City(it.name, it.slug))
                }

                cities.addAll(tools.sortCities(tmpCities))
                setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
                Log.i("Ошибка", errorMessage)
            }
        })
    }

    private fun setDataOnRecView() {
        val adapter = CityAdapter(cities)
        rec_view_cities.layoutManager = LinearLayoutManager(this)
        rec_view_cities.adapter = adapter

        adapter.onItemClick = { city ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("location", city.getSlug)
            intent.putExtra("cityName", city.getCityName)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    fun onClickCross(v: View) {
        onBackPressed()
    }
}