package com.zkatemor.kudago.util

import android.content.Context
import android.net.ConnectivityManager
import com.zkatemor.kudago.networks.Place
import java.text.DateFormatSymbols
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor


class Tools(private val context: Context) {

    fun convertPlace(place: Place?): String {
        var result: String = ""

        if (place != null) {
            if (place.title != null)
                result += place.title
            else
                if (place.address != null)
                    result += place.address
        }

        return result
    }

    fun convertDate(sDate: String?, eDate: String?): String {
        var result = ""

        var sMonth = ""
        var sDay = ""

        var eMonth = ""
        var eDay = ""

        if (sDate != null) {
            sMonth += sDate!!.substring(5, 7)
            sDay += sDate!!.substring(8)
        }

        if (eDate != null && !sDate.equals(eDate)) {
            eMonth += eDate!!.substring(5, 7)
            eDay += eDate!!.substring(8)
        }

        if (sDate != null) {
            result += sDay.toInt().toString()

            if (!sMonth.equals(eMonth))
                result += " " + DateFormatSymbols().getMonths()[sMonth.toInt() - 1]
        }

        if (eDate != null && !sDate.equals(eDate))
            result += " - " + eDay.toInt().toString() + " " + DateFormatSymbols().getMonths()[eMonth.toInt() - 1]

        return result
    }

    fun isConnected(): Boolean {
        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

    fun getCoordinates(place: Place?): ArrayList<Double> {
        val result: ArrayList<Double> = ArrayList()

        if (place != null) {
            if (place.coordinates.lat != null)
                result.add(place.coordinates.lat)

            if (place.coordinates.lon != null)
                result.add(place.coordinates.lon)
        }

        return result
    }

    fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}