package com.zkatemor.kudago.db

import android.arch.persistence.room.*
import com.zkatemor.kudago.models.EventCard

@Dao
interface EventCardDao {
    @Query("SELECT * FROM eventcard")
    fun getAll(): List<EventCard>

    @Query("SELECT * FROM eventcard WHERE id = :id")
    fun getById(id: Int): EventCard

    @Insert
    fun insert(event: EventCard)

    @Update
    fun update(event: EventCard)

    @Delete
    fun delete(event: EventCard)
}