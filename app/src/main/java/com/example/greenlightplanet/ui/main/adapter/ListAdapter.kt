package com.example.greenlightplanet.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.greenlightplanet.R
import com.example.greenlightplanet.model.*
import kotlinx.android.synthetic.main.item_layout.view.*
import java.util.*

class ListAdapter(onListItemClick: OnListItemClick) :
    RecyclerView.Adapter<ListAdapter.DataViewHolder>(), Filterable {

    private var performances: MutableList<Any>? = mutableListOf()
    private var performancesFiltered: MutableList<Any>? = mutableListOf()
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

    fun addData(list: MutableList<Any>) {
        performances?.clear()
        performancesFiltered?.clear()
        performances?.addAll(list)
        performancesFiltered?.addAll(list)
    }

    fun getAdapterDataList(): List<*>? {
        return performances
    }

    fun getAdapterItem(): Any? {
        if (!performances.isNullOrEmpty()) {
            return performances?.get(0)
        }
        return null
    }

    interface OnListItemClick {
        fun onListItemClicked(clickedItem: Any?)
    }

    override fun getFilter(): Filter {
        return listFilter
    }

    private val listFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Any> = mutableListOf()
            if (constraint == null || constraint.isEmpty()) {
                performancesFiltered?.let { filteredList.addAll(it) }
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.US).trim()
                if (performancesFiltered != null) {
                    for (item in performancesFiltered!!) {
                        when (item) {
                            is Country -> {
                                if (item.country?.toLowerCase(Locale.US)
                                        ?.contains(filterPattern) == true
                                ) {
                                    filteredList.add(item)
                                }
                            }
                            is Zone -> {
                                if (item.zone?.toLowerCase(Locale.US)
                                        ?.contains(filterPattern) == true
                                ) {
                                    filteredList.add(item)
                                }
                            }
                            is Region -> {
                                if (item.region?.toLowerCase(Locale.US)
                                        ?.contains(filterPattern) == true
                                ) {
                                    filteredList.add(item)
                                }
                            }
                            is Area -> {
                                if (item.area?.toLowerCase(Locale.US)
                                        ?.contains(filterPattern) == true
                                ) {
                                    filteredList.add(item)
                                }
                            }
                            is Employee -> {
                                if (item.name?.toLowerCase(Locale.US)
                                        ?.contains(filterPattern) == true
                                ) {
                                    filteredList.add(item)
                                }
                            }
                        }
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            performances?.clear()
            performances = p1?.values as MutableList<Any>?
            notifyDataSetChanged()
        }
    }
}