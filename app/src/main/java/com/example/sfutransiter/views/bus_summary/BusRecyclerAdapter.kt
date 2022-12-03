package com.example.sfutransiter.views.bus_summary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfutransiter.R
import com.example.sfutransiter.model.Bus

class BusRecyclerAdapter(private val buses: Array<Bus>) :
    RecyclerView.Adapter<BusRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val busItem = buses[position]

        holder.busDestination.text = busItem.destination
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val busDestination: TextView = view.findViewById(R.id.txt_destination)
    }

    override fun getItemCount(): Int = buses.size
}