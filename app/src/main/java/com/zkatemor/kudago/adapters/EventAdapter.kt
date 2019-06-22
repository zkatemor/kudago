package com.zkatemor.kudago.adapters

import android.content.Context
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
import com.zkatemor.kudago.util.Tools

class EventAdapter(private val items: ArrayList<EventCard>, context: Context)
    : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var onItemClick: ((EventCard) -> Unit)? = null

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewHolder: ViewGroup, position: Int): EventViewHolder {
        return EventViewHolder(LayoutInflater.from(viewHolder.context).inflate(R.layout.event_card_model,
            viewHolder, false))
    }

    override fun onBindViewHolder(viewHolder: EventViewHolder, position: Int) {
        val item = items[position]

        viewHolder.title.text = item.getTitle
        viewHolder.description.text = item.getDescription

        if (item.getLocation != ""){
            viewHolder.location.text = item.getLocation
            viewHolder.location_layout.visibility = View.VISIBLE
        }
        else
            viewHolder.location_layout.visibility = View.GONE

        if (item.getDate != "") {
            viewHolder.date.text = item.getDate
            viewHolder.date_layout.visibility = View.VISIBLE
        }
        else
            viewHolder.date_layout.visibility = View.GONE

        if (item.getCost != "") {
            viewHolder.cost.text = item.getCost
            viewHolder.cost_layout.visibility = View.VISIBLE
        }
        else
            viewHolder.cost_layout.visibility = View.GONE

        viewHolder.description.text = Tools.removeTags(item.getDescription)

        Glide.with(viewHolder.main_layout).load(item.getImageURL).into(viewHolder.image)
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