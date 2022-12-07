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

    var startStop = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.bus_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val busItem = buses[position]
        var dest = busItem.destination.replace("TO", "->")

        var destArray = dest.split("->")


        if (!(busItem.pattern.contains("E2A") or (busItem.pattern.contains("WBIN")) or (busItem.pattern.contains("NB1")) or (busItem.pattern.contains("E1")) )){
            dest = "SFU -> "
            if (destArray.size >= 2){
                dest += destArray[1]
                startStop = destArray[1]
            } else{
                dest += destArray[0]
                startStop = destArray[0]
            }

        }
        if (!(dest.contains("SFU ->"))){
            dest = startStop + " -> SFU"
        }

        if (dest == "-> SFU"){
            dest = startStop + "-> SFU"
        }

        holder.busDestination.text = dest
        holder.departedAt.text = busItem.vehicleNo
        holder.lastUpdatedAt.text = "Last Updated At: ${busItem.recordedTime}"
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val busDestination: TextView = view.findViewById(R.id.txt_destination)
        val departedAt: TextView = view.findViewById(R.id.txtETA)
        val lastUpdatedAt: TextView = view.findViewById(R.id.txtTimeStamp)
    }

    override fun getItemCount(): Int = buses.size
}