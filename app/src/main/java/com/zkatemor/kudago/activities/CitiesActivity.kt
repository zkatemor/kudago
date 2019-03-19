package com.zkatemor.kudago.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.CityAdapter
import com.zkatemor.kudago.models.City
import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.ResponseCallback
import com.zkatemor.kudago.util.CitiesRepository
import kotlinx.android.synthetic.main.activity_cities.*

class CitiesActivity : AppCompatActivity() {

    private val cities: ArrayList<City> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)
        addCities()
    }

    private fun addCities() {
        CitiesRepository.instance.getCities(object : ResponseCallback<CitiesResponse> {

            override fun onSuccess(apiResponse: CitiesResponse) {
                apiResponse.cities.forEach{
                    cities.add(City(it.name))
                }
                setDataOnRecView()
            }

            override fun onFailure(errorMessage: String) {
                Log.i("Ошибка", errorMessage)
            }
        })
    }

    private fun setDataOnRecView(){
        val adapter = CityAdapter(cities)
        rec_view_cities.layoutManager = LinearLayoutManager(this)
        rec_view_cities.adapter = adapter

        adapter.onItemClick = {city ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun onClickCross(v: View) {
        onBackPressed()
    }
}