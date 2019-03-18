package com.zkatemor.kudago.networks

import com.google.gson.annotations.SerializedName

interface ApiResponse

data class EventsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val events: List<Event>
) : ApiResponse

data class Event(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("body_text")
    val fullDescription: String,
    @SerializedName("place")
    var place: Place?,
    @SerializedName("dates")
    var dates: List<Date>,
    @SerializedName("price")
    var price: String,
    @SerializedName("images")
    val images: ArrayList<Image>
)

data class Place(
    @SerializedName("title")
    val title: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("coords")
    val coordinates: Coordinates
)

data class Image(
    @SerializedName("image")
    val image: String
)

data class Date(
    @SerializedName("start_date")
    var start_date: String?,
    @SerializedName("end_date")
    var end_date: String?
)

data class Coordinates(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)