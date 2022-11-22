package com.example.sfutransiter.views

import android.os.Bundle
import com.example.sfutransiter.R
import com.example.sfutransiter.databinding.ActivityMainBinding
import com.example.sfutransiter.views.components.BaseActivity
import com.example.sfutransiter.views.search_by.SearchBy
import com.example.sfutransiter.views.select_bus.SelectBus

class MainActivity : BaseActivity(),
    MainFragment.MainFragmentInterface,
    SearchBy.SearchByFragmentInterface {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment(R.id.mainFragmentContainer, MainFragment.newInstance(), MainFragment.TAG, false)
    }

    override fun swapToSearchBy() {
        replaceFragment(R.id.mainFragmentContainer, SearchBy.newInstance(), SearchBy.TAG, false)
    }

    override fun swapToSelectBus() {
        replaceFragment(R.id.mainFragmentContainer, SelectBus.newInstance(), SelectBus.TAG)
    }
}