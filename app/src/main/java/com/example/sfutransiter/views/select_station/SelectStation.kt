package com.example.sfutransiter.views.select_station

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfutransiter.R
import com.example.sfutransiter.backend.RetrofitAPI
import com.example.sfutransiter.databinding.FragmentSelectStationBinding
import com.example.sfutransiter.model.BusStop
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.TransitViewModel
import com.example.sfutransiter.repository.TLinkRepo
import com.example.sfutransiter.views.MainFragment
import retrofit2.Response
import kotlin.math.roundToInt

class SelectStation : Fragment(), LocationListener {
    private var _binding: FragmentSelectStationBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectStationInterface: SelectStationInterface
    private lateinit var locationManager : LocationManager
    private lateinit var currentLocation : Location
    private lateinit var nearbyStops: LiveData<Response<Array<BusStop>>>
    private lateinit var listStops: LiveData<Response<Array<BusStop>>>
    private lateinit var adapterList : StationListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SelectStationInterface)
            selectStationInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement SelectStationInterface")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectStationBinding.inflate(inflater, container, false)

        val repo = TLinkRepo(RetrofitAPI.getTransLinkInstance())
        val viewModelFactory = MyViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TransitViewModel::class.java]

        getCurrentLocation()
        nearbyStops = viewModel.getStopsNear(roundToSix(currentLocation.latitude),roundToSix(currentLocation.longitude), 200)

        //Get all related stops near SFU Burnaby
        val sfuLat = 49.2781
        val sfuLng = -122.9199
        listStops = viewModel.getStopsNear(sfuLat,sfuLng,2000,"143")
        updateStopList()
        listStops = viewModel.getStopsNear(sfuLat,sfuLng,2000,"144")
        updateStopList()
        listStops = viewModel.getStopsNear(sfuLat,sfuLng,2000,"145")
        updateStopList()
        listStops = viewModel.getStopsNear(sfuLat,sfuLng,2000,"R5")
        updateStopList()

        //Display RecyclerLayouts
        val stationNBView : RecyclerView = binding.root.findViewById(R.id.stationsNBList)
        val stationListView : RecyclerView = binding.root.findViewById(R.id.stationsList)
        val adapterNB = StationNBAdapter(selectStationInterface)
        adapterList = StationListAdapter(selectStationInterface)
        stationNBView.adapter = adapterNB
        stationListView.adapter = adapterList

        //Required to display the second RecyclerView
        stationListView.layoutManager = LinearLayoutManager(requireContext())

        nearbyStops.observe(viewLifecycleOwner) {
            it.body()?.let { it1 -> adapterNB.replaceList(it1) }
            adapterNB.notifyDataSetChanged()
        }

        return binding.root
    }

    private fun getCurrentLocation() {
        locationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()

        criteria.accuracy = Criteria.ACCURACY_FINE
        val provider: String? = locationManager.getBestProvider(criteria, true)

        if (provider != null) {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                Log.e(MainFragment.TAG, "getCurrentLocation: Locations permissions not granted")
                return
            }
            currentLocation = locationManager.getLastKnownLocation(provider)!!
            if(currentLocation != null)
                onLocationChanged(currentLocation)
            locationManager.requestLocationUpdates(provider,10000,10f,this)
        }
    }

    override fun onLocationChanged(location: Location) {
        currentLocation = location
    }

    //Trans link API only accepts up to six decimals places for lat/long values
    private fun roundToSix(num : Double) : Double {
        return (num * 1000000.0).roundToInt() / 1000000.0
    }

    private fun updateStopList() {
        listStops.observe(viewLifecycleOwner) {
            it.body()?.let { it1 -> adapterList.replaceList(it1) }
            adapterList.notifyDataSetChanged()
        }
    }

    interface SelectStationInterface {
        fun swapToBusSummary(routeId : String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SelectStation()

        val TAG: String = SelectStation::class.java.simpleName
    }
}