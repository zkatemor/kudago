package com.zkatemor.kudago.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.zkatemor.kudago.models.City
import com.zkatemor.kudago.networks.CitiesResponse
import com.zkatemor.kudago.networks.ResponseCallback

class CitiesViewModel: ViewModel() {
    private lateinit var cities: MutableLiveData<ArrayList<City>>

    init {
        if (!::cities.isInitialized) {
            cities = MutableLiveData()
            loadCities()
        }
    }

    fun getCities(): LiveData<ArrayList<City>> = cities

    private fun loadCities() {
        val citiesFromServer: ArrayList<City> = ArrayList()
        CitiesRepository.instance.getCities(object : ResponseCallback<java.util.ArrayList<CitiesResponse>> {
            override fun onSuccess(apiResponse: java.util.ArrayList<CitiesResponse>) {
                apiResponse.forEach {
                    citiesFromServer.add(City(it.name, it.slug))
                }
                cities.value=citiesFromServer
            }

            override fun onFailure(errorMessage: String) {
                Log.i("CitiesViewModel error", errorMessage)
            }
        })
    }
}