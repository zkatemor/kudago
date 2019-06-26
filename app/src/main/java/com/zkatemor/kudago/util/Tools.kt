package com.zkatemor.kudago.util

import android.content.Context
import android.net.ConnectivityManager
import com.zkatemor.kudago.networks.Place
import java.text.DateFormatSymbols
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.support.v4.content.ContextCompat
import android.text.Html
import android.text.Spanned
import com.google.android.gms.maps.model.BitmapDescriptor
import com.zkatemor.kudago.models.City
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class Tools {
    companion object {
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

        private fun isConnected(context: Context): Boolean {
            return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }

        fun isInternetAccess(context: Context): Boolean {
            if (isConnected(context)) {
                try {
                    val process = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com")
                    val value = process.waitFor()
                    return value == 0
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return false
            } else return false
        }

        fun connectGoogle(): Boolean {
            try {
                val urlc = URL("http://www.google.com").openConnection() as HttpURLConnection
                urlc.setRequestProperty("User-Agent", "Test")
                urlc.setRequestProperty("Connection", "close")
                urlc.connectTimeout = 10000
                urlc.connect()
                return urlc.responseCode === 200
            } catch (e: IOException) {
                //log("IOException in connectGoogle())")
                return false
            }

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
                Bitmap.createBitmap(
                    vectorDrawable.intrinsicWidth,
                    vectorDrawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)

            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }

        fun removeTags(input: String?): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY).trim().toString()
            } else {
                Html.fromHtml(input).toString()
            }
        }

        fun getSpanned(input: String?): Spanned? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(input, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(input)
            }
        }

        fun sortCities(cities: ArrayList<City>): ArrayList<City> {
            cities.removeAll { it.getCityName == "Москва" || it.getCityName == "Санкт-Петербург" }

            cities.add(0, City("Санкт-Петербург", "spb"))
            cities.add(0, City("Москва", "msk"))

            return cities
        }
    }
}