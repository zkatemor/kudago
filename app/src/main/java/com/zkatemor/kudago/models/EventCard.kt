package com.zkatemor.kudago.models

class EventCard(private var title : String, private var description: String, private var location: String,
                private var date : String, private var cost : Int, private var imageId : Int) {

    val getTitle: String
        get() = title

    val getDescription: String
        get() = description

    val getLocation: String
        get() = location

    val getDate: String
        get() = date

    val getCost: Int
        get() = cost

    val getImageId: Int
        get() = imageId
}