package com.zkatemor.kudago.models

import java.io.Serializable

class EventCard : @Transient Serializable {

    private var id: Int = 0
    private var title: String = ""
    private var description: String = ""
    private var fullDescription: String = ""
    private var location: String = ""
    private var date: String = ""
    private var cost: String = ""
    private var imagesURL: ArrayList<String> = ArrayList()
    private var coordinates: ArrayList<Double> = ArrayList()

    constructor(
        id: Int,
        title: String,
        description: String,
        fullDescription: String,
        location: String,
        date: String,
        cost: String,
        imagesURL: ArrayList<String>,
        coordinates: ArrayList<Double>
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.fullDescription = fullDescription
        this.location = location
        this.date = date
        this.cost = cost
        this.imagesURL = imagesURL
        this.coordinates = coordinates
    }

    val getId: Int
        get() = id

    val getTitle: String
        get() = title

    val getDescription: String
        get() = description

    val getFullDescription: String
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