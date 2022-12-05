package com.example.sfutransiter.views.bus_summary

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.sfutransiter.R
import com.example.sfutransiter.backend.RetrofitAPI
import com.example.sfutransiter.databinding.FragmentBusSummaryBinding
import com.example.sfutransiter.model.Bus
import com.example.sfutransiter.model.StopEstimate
import com.example.sfutransiter.model.User
import com.example.sfutransiter.model.view_model.BusReviewViewModel
import com.example.sfutransiter.model.view_model.MyViewModelFactory
import com.example.sfutransiter.model.view_model.TransitViewModel
import com.example.sfutransiter.repository.AWSRepo
import com.example.sfutransiter.repository.TLinkRepo
import com.example.sfutransiter.views.MainFragment
import retrofit2.Response

private const val ARG_ROUTE_ID = "routeId"

class BusSummary : Fragment() {
    private var _binding: FragmentBusSummaryBinding? = null
    private val binding get() = _binding!!

    private lateinit var busSummaryInterface: BusSummaryInterface
    private lateinit var routeId: String
    private lateinit var buses: LiveData<Response<Array<Bus>>>
    private lateinit var stopEstimates: LiveData<Response<Array<StopEstimate>>>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BusSummaryInterface)
            busSummaryInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement BusSummaryInterface")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routeId = it.getString(ARG_ROUTE_ID)!!
        }
        val repo = TLinkRepo(RetrofitAPI.getTransLinkInstance())
        val viewModelFactory = MyViewModelFactory(repo)
        val viewModel = ViewModelProvider(this, viewModelFactory)[TransitViewModel::class.java]

        val regex = Regex("\\d{5}")
        buses = if (routeId.matches(regex))
            viewModel.getBusesByStop(routeId)
        else
            viewModel.getBusesByRoute(routeId)

        val awsRepo = AWSRepo(RetrofitAPI.getAWSInstance())
        val awsViewModelFactory = MyViewModelFactory(awsRepo)
        val awsViewModel =
            ViewModelProvider(this, awsViewModelFactory)[BusReviewViewModel::class.java]

        Log.d(TAG, "onCreate: ${User.getCurrentUser()}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBusSummaryBinding.inflate(inflater, container, false)

        binding.txtBusTitle.text = routeId
        setupRating()
        setupCommentBtn()
        setupList()

        return binding.root
    }

    private fun setupList() {
        buses.observe(viewLifecycleOwner) {
            if (!it.isSuccessful) return@observe
            val buss = it.body()!!
            binding.txtBusTotal.text = getString(R.string.currently_n_buses_on_road, buss.size)
            binding.recyclerBuses.adapter = BusRecyclerAdapter(buss)

        }
    }

    private fun setupCommentBtn() {
        binding.btnComments.setOnClickListener {
            busSummaryInterface.swapToCommentBoard(routeId)
        }
    }

    private fun setupRating() {
//        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
//            // TODO save to db
//        }
    }

    interface BusSummaryInterface {
        fun swapToCommentBoard(routeNo: String)
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