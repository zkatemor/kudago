package com.zkatemor.kudago.db

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.zkatemor.kudago.models.EventCard

@Database(entities  = [EventCard::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventCardDao(): EventCardDao
}