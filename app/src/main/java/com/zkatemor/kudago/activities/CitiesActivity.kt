package com.zkatemor.kudago.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zkatemor.kudago.R
import com.zkatemor.kudago.adapters.CityAdapter
import com.zkatemor.kudago.models.City
import kotlinx.android.synthetic.main.activity_cities.*

class CitiesActivity : AppCompatActivity() {

    private val cities: ArrayList<City> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cities)

        addCities()
        rec_view_cities.layoutManager = LinearLayoutManager(this)
        rec_view_cities.adapter = CityAdapter(cities)
    }

    fun addCities(){
        cities.add(City("Москва"))
        cities.add(City("Санкт-Петербург"))
        cities.add(City("Киев"))
        cities.add(City("Нижний Новгород"))
        cities.add(City("Сочи"))
        cities.add(City("Екатеринбург"))
        cities.add(City("Норильск"))
    }

    fun onClickCross(v : View){
        onBackPressed()
    }
}