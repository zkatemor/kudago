package com.zkatemor.kudago.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zkatemor.kudago.R
import com.zkatemor.kudago.models.City

class CityAdapter(private val items: ArrayList<City>, private val location: String?)
    : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    var onItemClick: ((City) -> Unit)? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): CityViewHolder {
        return CityViewHolder(LayoutInflater.from(viewGroup.context).
            inflate(R.layout.city_model, viewGroup, false))
    }

    override fun onBindViewHolder(viewHolder: CityViewHolder, position:  Int) {
        val item = items[position]

        viewHolder.text_view_city.text = item.getCityName
        if (item.getSlug.equals(location))
            viewHolder.image_view_check.visibility = View.VISIBLE
    }

    inner class CityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var text_view_city: TextView
        var image_view_check: ImageView

        init {
            super.itemView
            text_view_city = itemView.findViewById(R.id.text_view_city) as TextView
            image_view_check = itemView.findViewById(R.id.image_view_check) as ImageView

            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}