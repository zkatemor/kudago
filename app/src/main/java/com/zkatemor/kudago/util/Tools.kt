package com.zkatemor.kudago.util

import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat.getSystemService
import com.zkatemor.kudago.networks.Place
import java.text.DateFormatSymbols

class Tools(private val context: Context){

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
        var result: String = ""
        var sMonth: String = ""
        var sDay: String = ""
        var eMonth: String = ""
        var eDay: String = ""

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
}