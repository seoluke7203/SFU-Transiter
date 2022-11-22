package com.example.sfutransiter.views.bus_summary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.databinding.FragmentBusSummaryBinding

class BusSummary : Fragment() {
    private var _binding: FragmentBusSummaryBinding? = null
    private val binding get() = _binding!!

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
        fun newInstance() = BusSummary()

        val TAG = BusSummary::class.java.simpleName
    }
}