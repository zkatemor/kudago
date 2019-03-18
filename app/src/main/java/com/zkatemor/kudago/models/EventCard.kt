package com.zkatemor.kudago.models

class EventCard(
    private val id: Int,
    private var title: String,
    private var description: String,
    private var fullDescription: String,
    private var location: String,
    private var date: String,
    private var cost: String,
    private var imagesURL: ArrayList<String>,
    private var coordinates: ArrayList<Double>
) {
    val getId: Int
        get() = id

    val getTitle: String
        get() = title

    val getDescription: String
        get() = description

    val getFullDesctiption: String
        get() = fullDescription

    val getLocation: String
        get() = location

    val getDate: String
        get() = date

    val getCost: String
        get() = cost

    val getImageURL: String
        get() = imagesURL[0]

    val getImages: ArrayList<String>
        get() = imagesURL

    val getCoordinates: ArrayList<Double>
       get() = coordinates
}