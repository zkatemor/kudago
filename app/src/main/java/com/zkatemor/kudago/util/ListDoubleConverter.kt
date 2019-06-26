package com.zkatemor.kudago.util

import android.arch.persistence.room.TypeConverter

class ListDoubleConverter {
    @TypeConverter
    fun fromList(list: ArrayList<Double>): String {
       /* val result = ArrayList<String>()
        list.forEach {
            result.add(it.toString())
        }*/
        return list.joinToString { "/" }
    }

    @TypeConverter
    fun toList(data: String): ArrayList<Double> {
        val result = ArrayList<Double>()
        val value = data.split("/") as ArrayList
        value.forEach {
            result.add(it.toDouble())
        }
        return result
    }

}