package com.example.sfutransiter.views.select_bus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.databinding.FragmentSelectBusBinding

class SelectBus : Fragment() {
    private var _binding: FragmentSelectBusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectBusBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SelectBus()

        val TAG = SelectBus::class.java.simpleName
    }
}