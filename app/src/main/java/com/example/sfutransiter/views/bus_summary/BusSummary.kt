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
import com.example.sfutransiter.model.StopEstimate
import com.example.sfutransiter.model.view_model.BusReviewViewModel
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.TransitViewModel
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.repository.TLinkRepo
import retrofit2.Response

private const val ARG_ROUTE_ID = "routeId"

class BusSummary : Fragment() {
    private var _binding: FragmentBusSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var routeId: String
    private lateinit var buses: LiveData<Response<Array<Bus>>>
    private lateinit var stopEstimates: LiveData<Response<Array<StopEstimate>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routeId = it.getString(ARG_ROUTE_ID)!!
        }
        val repo = TLinkRepo(RetrofitAPI.getTransLinkInstance())
        val viewModelFactory = MyViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TransitViewModel::class.java]
        buses = viewModel.getBusesByRoute(routeId)
        // TODO get stop estimates
        // stopEstimates = viewModel.getStopEstimates(52807, routeNo = routeId)

        val awsRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val awsViewModelFactory = MyViewModelFactory(awsRepo)
        val awsViewModel =
            ViewModelProvider(this, awsViewModelFactory)[BusReviewViewModel::class.java]
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