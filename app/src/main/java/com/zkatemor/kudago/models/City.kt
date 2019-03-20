package com.zkatemor.kudago.models

class City(private var cityName: String, private var slug: String) {

    val getCityName: String
        get() = cityName

    val getSlug: String
        get() = slug
}