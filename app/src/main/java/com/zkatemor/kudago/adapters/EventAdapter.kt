package com.zkatemor.kudago.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zkatemor.kudago.R
import com.zkatemor.kudago.models.EventCard
import android.widget.TextView
import android.widget.ImageView

class EventAdapter(private val items: ArrayList<EventCard>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.event_card, p0, false))
    }

    override fun onBindViewHolder(p0: EventViewHolder, p1: Int) {
        val item = items[p1]

        p0.title.text = item.getTitle
        p0.description.text = item.getDescription
        p0.location.text = item.getLocation
        p0.date.text = item.getDate
        p0.cost.text = item.getCost.toString()
        p0.image.setImageResource(item.getImageId)
    }

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var description: TextView
        var location: TextView
        var date: TextView
        var cost: TextView
        var image: ImageView

        init {
            super.itemView
            title = itemView.findViewById(R.id.text_view_title) as TextView
            description = itemView.findViewById(R.id.text_view_description) as TextView
            location = itemView.findViewById(R.id.text_view_location) as TextView
            date = itemView.findViewById(R.id.text_view_date) as TextView
            cost = itemView.findViewById(R.id.text_view_cost) as TextView
            image = itemView.findViewById(R.id.image_view_event) as ImageView
        }
    }
}