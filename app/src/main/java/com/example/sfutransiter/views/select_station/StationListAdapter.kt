package com.example.sfutransiter.views.select_station

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfutransiter.R
import com.example.sfutransiter.model.BusStop

class StationListAdapter(myInterface: SelectStation.SelectStationInterface) : RecyclerView.Adapter<StationListAdapter.ViewHolder>() {

    private var myStops = ArrayList<BusStop>()
    private var selectInterface = myInterface

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.station_list_item, parent,false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val busItem = myStops.get(position)

        holder.stationName.text = busItem.name
        holder.stationName.setOnClickListener {
            selectInterface.swapToBusSummary(busItem.stopNo.toString())
        }
    }

    override fun getItemCount(): Int {
        return myStops.size
    }

    fun replaceList(newArray: Array<BusStop>){
        myStops.addAll(newArray)
        myStops.sortBy { it.name }
        myStops = myStops.distinctBy { it.stopNo } as ArrayList<BusStop>
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val stationName: TextView = itemView.findViewById(R.id.txtStationListItem)
    }
}