package com.zkatemor.kudago.util

import android.arch.persistence.room.TypeConverter
import kotlin.collections.ArrayList

class StringListConverter {
    @TypeConverter
    fun fromList(list: ArrayList<String>): String {
        return list.joinToString { "#" }
    }

    @TypeConverter
    fun toList(data: String): ArrayList<String> {
        return data.split("#") as ArrayList
    }

}