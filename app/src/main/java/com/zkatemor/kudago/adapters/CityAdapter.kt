package com.zkatemor.kudago.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zkatemor.kudago.R
import com.zkatemor.kudago.models.City

class CityAdapter(private val items: ArrayList<City>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CityAdapter.CityViewHolder {
        return CityAdapter.CityViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.city_model, p0, false))
    }

    override fun onBindViewHolder(p0: CityViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //

        init {
            super.itemView
           //
        }
    }
}