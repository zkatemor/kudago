package com.zkatemor.kudago.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zkatemor.kudago.R
import com.zkatemor.kudago.models.EventCard
import android.widget.TextView
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide


class EventAdapter(private val items: ArrayList<EventCard>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var onItemClick: ((EventCard) -> Unit)? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.event_card_model, p0, false))
    }

    override fun onBindViewHolder(p0: EventViewHolder, p1: Int) {
        val item = items[p1]

        p0.title.text = item.getTitle
        p0.description.text = item.getDescription

        if (item.getLocation != ""){
            p0.location.text = item.getLocation
            p0.location_layout.visibility = View.VISIBLE
        }
        else
            p0.location_layout.visibility = View.GONE

        if (item.getDate != "")
            p0.date.text = item.getDate
        else
            p0.date_layout.visibility = View.GONE

        if (item.getCost != "")
            p0.cost.text = item.getCost
        else
            p0.cost_layout.visibility = View.GONE

        Glide.with(p0.main_layout).load(item.getImageURL).into(p0.image)
    }

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var main_layout: LinearLayout
        var location_layout: LinearLayout
        var date_layout: LinearLayout
        var cost_layout: LinearLayout
        var title: TextView
        var description: TextView
        var location: TextView
        var date: TextView
        var cost: TextView
        var image: ImageView

        init {
            super.itemView
            main_layout = itemView.findViewById(R.id.main_layout) as LinearLayout
            cost_layout = itemView.findViewById(R.id.cost_layout) as LinearLayout
            date_layout = itemView.findViewById(R.id.date_layout) as LinearLayout
            location_layout = itemView.findViewById(R.id.location_layout) as LinearLayout
            title = itemView.findViewById(R.id.text_view_title) as TextView
            description = itemView.findViewById(R.id.text_view_description) as TextView
            location = itemView.findViewById(R.id.text_view_location) as TextView
            date = itemView.findViewById(R.id.text_view_date) as TextView
            cost = itemView.findViewById(R.id.text_view_cost) as TextView
            image = itemView.findViewById(R.id.image_view_event) as ImageView

            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}