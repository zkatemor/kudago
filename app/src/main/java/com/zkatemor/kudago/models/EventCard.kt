package com.zkatemor.kudago.models

import android.arch.persistence.room.*
import com.zkatemor.kudago.util.StringListConverter
import java.io.Serializable

@Entity
class EventCard : @Transient Serializable {
    @PrimaryKey (autoGenerate = true)
    var id: Int = 0
    var title: String = ""
    var description: String = ""
    var fullDescription: String = ""
    var location: String = ""
    var date: String = ""
    var cost: String = ""
    @TypeConverters(StringListConverter::class)
    var imagesURL: ArrayList<String> = ArrayList()
    @Ignore
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

    constructor(
        title: String,
        description: String,
        fullDescription: String,
        location: String,
        date: String,
        cost: String,
        imagesURL: ArrayList<String>
    ) {
        this.title = title
        this.description = description
        this.fullDescription = fullDescription
        this.location = location
        this.date = date
        this.cost = cost
        this.imagesURL = imagesURL
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