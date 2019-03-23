package com.zkatemor.kudago.models

import java.io.Serializable

class City: @kotlin.jvm.Transient Serializable {

    private var cityName: String = ""
    private var slug: String = ""

    constructor(cityName: String, slug: String){
        this.cityName = cityName
        this.slug = slug
    }

    val getCityName: String
        get() = cityName

    val getSlug: String
        get() = slug
}