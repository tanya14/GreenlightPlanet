package com.example.greenlightplanet.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlightplanet.R
import com.example.greenlightplanet.model.*
import kotlinx.android.synthetic.main.item_layout.view.*

class ListAdapter(onListItemClick: OnListItemClick) : RecyclerView.Adapter<ListAdapter.DataViewHolder>() {

    private var performances: List<*>? = null
    private var onListItemClick: OnListItemClick? = null

    init {
        this.onListItemClick = onListItemClick
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(performance: Any?) {
            when (performance) {
                is Country -> {
                    itemView.zoneNameTV?.text = (performance).country
                }
                is Zone -> {
                    itemView.zoneNameTV?.text = (performance).zone
                }
                is Region -> {
                    itemView.zoneNameTV?.text = (performance).region
                }
                is Area -> {
                    itemView.zoneNameTV?.text = (performance).area
                }
                is Employee -> {
                    itemView.zoneNameTV?.text = (performance).name
                }
            }

            itemView.setOnClickListener {
                onListItemClick?.onListItemClicked(performance)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = performances?.size ?: 0

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(performances?.get(position))

    fun addData(list: List<*>) {
        performances = list
    }

    fun getAdapterDataList(): List<*>? {
        return performances
    }

    interface OnListItemClick {
        fun onListItemClicked(clickedItem: Any?)
    }
}