package com.zkatemor.kudago.di

import com.zkatemor.kudago.activities.CitiesActivity
import com.zkatemor.kudago.activities.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun injectsMainActivity(mainActivity: MainActivity)
    fun injectsCitiesActivity(citiesActivity: CitiesActivity)
}