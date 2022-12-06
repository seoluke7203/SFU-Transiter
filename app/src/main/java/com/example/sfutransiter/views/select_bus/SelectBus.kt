package com.example.sfutransiter.views.select_bus

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.R
import com.example.sfutransiter.databinding.FragmentSelectBusBinding
import com.example.sfutransiter.model.RouteID
import com.example.sfutransiter.views.MainFragment

class SelectBus : Fragment() {
    private var _binding: FragmentSelectBusBinding? = null
    private val binding get() = _binding!!

    private lateinit var selectBusInterface: SelectBusInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SelectBusInterface)
            selectBusInterface = context
        else Log.e(MainFragment.TAG, "onAttach: MainActivity must implement SelectBusInterface")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectBusBinding.inflate(inflater, container, false)

        setupButtons()

        return binding.root
    }

    private fun setupButtons() {


        binding.btn144.setOnClickListener {
            selectBusInterface.swapToBusSummary(RouteID.`144`)
        }
        binding.btn145.setOnClickListener {
            selectBusInterface.swapToBusSummary(RouteID.`145`)
        }
        binding.btn143.setOnClickListener {
            selectBusInterface.swapToBusSummary(RouteID.`143`)
        }
        binding.btnR5.setOnClickListener {
            selectBusInterface.swapToBusSummary(RouteID.R5)
        }
    }

    interface SelectBusInterface {
        fun swapToBusSummary(routeId: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SelectBus()

        val TAG: String = SelectBus::class.java.simpleName
    }
}