package com.example.greenlightplanet.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlightplanet.R
import com.example.greenlightplanet.model.Performance
import kotlinx.android.synthetic.main.item_layout.view.*

class ListAdapter(
    private val performances: ArrayList<Performance>
) : RecyclerView.Adapter<ListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(performance: Performance?) {
            itemView.zoneNameTV?.text = performance?.zone?.get(0)?.zone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = performances.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(performances[position])

    fun addData(list: List<Performance>) {
        performances.addAll(list)
    }
}