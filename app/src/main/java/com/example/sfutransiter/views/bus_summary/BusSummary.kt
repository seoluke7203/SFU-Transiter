package com.example.sfutransiter.views.bus_summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.sfutransiter.backend.RetrofitAPI
import com.example.sfutransiter.databinding.FragmentBusSummaryBinding
import com.example.sfutransiter.model.Bus
import com.example.sfutransiter.model.MyViewModelFactory
import com.example.sfutransiter.model.TransitViewModel
import com.example.sfutransiter.repository.Repository
import retrofit2.Response

private const val ARG_ROUTE_ID = "routeId"

class BusSummary : Fragment() {
    private var _binding: FragmentBusSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var routeId: String
    private lateinit var buses: LiveData<Response<Array<Bus>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routeId = it.getString(ARG_ROUTE_ID)!!
        }
        val repo = Repository(RetrofitAPI.getInstance())
        val viewModelFactory = MyViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TransitViewModel::class.java]
        buses = viewModel.getBusesByRoute(routeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBusSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(routeId: String) =
            BusSummary().apply {
                arguments = Bundle().apply {
                    putString(ARG_ROUTE_ID, routeId)
                }
            }

        val TAG: String = BusSummary::class.java.simpleName
    }
}