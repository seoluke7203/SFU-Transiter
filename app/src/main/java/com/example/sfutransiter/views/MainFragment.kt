package com.example.sfutransiter.views

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sfutransiter.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainFragmentInterface: MainFragmentInterface

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainFragmentInterface)
            mainFragmentInterface = context
        else Log.e(TAG, "onAttach: MainActivity must implement MainFragmentInterface")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        setupButton()

        return binding.root
    }

    private fun setupButton() {
        binding.btnAnonymous.setOnClickListener {
            mainFragmentInterface.swapToSearchBy()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()

        val TAG: String = MainFragment::class.java.simpleName
    }

    interface MainFragmentInterface {
        fun swapToSearchBy()
    }
}